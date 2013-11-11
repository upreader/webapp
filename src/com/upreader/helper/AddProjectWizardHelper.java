package com.upreader.helper;

import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.transfer.Upload;
import com.crocodoc.UprCrocodocSession;
import com.crocodoc.CrocodocDocument;
import com.crocodoc.CrocodocException;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.upreader.BusinessException;
import com.upreader.UpreaderApplication;
import com.upreader.UpreaderConstants;
import com.upreader.aws.AmazonService;
import com.upreader.context.Context;
import com.upreader.controller.BasicController;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.dto.AddProjectWizardDTO;
import com.upreader.dto.AmazonS3FileDetails;
import com.upreader.dto.AmazonS3FileDetailsBuilder;
import com.upreader.dto.CrocodocResultDTO;
import com.upreader.model.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import static com.google.common.base.Strings.emptyToNull;



public class AddProjectWizardHelper extends BasicController{
    private AddProjectWizardDTO wizardData;
    private Logger log = Logger.getLogger(AddProjectWizardHelper.class);

    public AddProjectWizardHelper(BasicPathHandler handler, Context context){
            super(handler,context);
    }

    public boolean incrementProjectNoViews(){
        String projectId = context().request().getParameter("projectId");
        Project project = context().projectDAO().get(Integer.valueOf(projectId));
        project.setNoViews(project.getNoViews() == null ? 0 : project.getNoViews()+1);
        try{
            context().projectDAO().update(project);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Function used to upload a project file in the
     * appropriate bucket and the appropriate folder
     *
     * @return
     */
    public boolean uploadProjectFile(){
      boolean isPublic = false;
      String  prefix   = "";
      String crocoUUID = "";

      String fType       = context().request().getParameter("fileType");
      User loggedUser = (User) context().session().getObject(UpreaderConstants.SESSION_USER);
      if(fType == null){
          return !handler().serverError("Inconsistent File Type details.").isEmpty();
      }

      //Read the file from request.
      final FileItem uploadedFile = context().request().getPart("file");
      //uploading file
      try{
        if(fType.equals(UpreaderConstants.PUBLIC_IMAGE)){
            isPublic = true;
            prefix = "images/";
        }
        if(fType.equals(UpreaderConstants.STORY)){
            isPublic = false;
            prefix = "stories/";
        }
        if(fType.equals(UpreaderConstants.STORY_SAMPLE)){
            isPublic = true;
            prefix = "samples/";

            //upload to crocodoc
            File tmpDir = new File(handler().uploadFolder() + "/"); tmpDir.mkdirs();
            File tmpFile = new File(tmpDir, uploadedFile.getName());
            OutputStream outputStream = new FileOutputStream(tmpFile);
            IOUtils.copy(uploadedFile.getInputStream(), outputStream);
            outputStream.close();
            crocoUUID = CrocodocDocument.upload(tmpFile);
            System.out.println("Croco UUIIDDD" + crocoUUID);
        }
        if(fType.equals(UpreaderConstants.PROOF_DOCUMENT)){
            isPublic = false;
            prefix = "proofDocuments/";
        }
        prefix += loggedUser.getEmail() + "/";

        //upload to amazon
    	AmazonService aws = handler().getApplication().getAmazonService();
        final Upload upload = aws.uploadFile(isPublic, prefix + uploadedFile.getName(), uploadedFile.getInputStream(), new ProgressListener() {
					@Override
					public void progressChanged(ProgressEvent evt) {
					}
				});
				upload.waitForCompletion();
                String uploadedKey = prefix + uploadedFile.getName();
                return handler().json(new AmazonS3FileDetailsBuilder().withFileName(uploadedFile.getName())
                                                                      .withKey(uploadedKey)
                                                                      .withIsPublicFlag(isPublic)
                                                                      .withCrocoUUID(crocoUUID)
                                                                      .build());
      }catch(Exception e){
          log.error(e);
          return !handler().serverError(e.getMessage()).isEmpty();
      }finally {
          //clean the file
          File tmpFile = new File(handler().uploadFolder() + "/" + uploadedFile.getName());
          //if(tmpFile != null && tmpFile.exists()){tmpFile.delete();};
      }
    }

    public boolean getWizardDataJson() {
        processWizardData();
        return handler().json(getWizardData());
    }

    public boolean getPostProjectResult(){
        processWizardData();
        try{
            createAndInsertNewProject(wizardData);
            return handler().json(getWizardData());
        }catch(BusinessException be){
           return false;
        }
    }

    private boolean processWizardData(){
        wizardData = getSessionWizardData();
        syncDTO();
        return true;
    }

    private boolean syncDTO() {
        Optional<String> receivedWizardDataJson = Optional.fromNullable(emptyToNull(context().query().get("jsonWizData")));
        if(receivedWizardDataJson.isPresent()){
            try {
                AddProjectWizardDTO receivedWizardDTO = (AddProjectWizardDTO)WebHelper.fromJson(AddProjectWizardDTO.class, context().query().get("jsonWizData"));
                wizardData.sync(receivedWizardDTO);

                if(wizardData.getCurrentStep().equals(6)){
                    addSessionKeyToWizardData();
                }
                //Sync session data
                context().session().putObject(UpreaderConstants.SESSION_NEWPROJECT_WIZ, wizardData);
            } catch (IOException e) {
                context().getDispatcher().dispatchException(context(), e);
            }
        }
        return true;
    }

    private void addSessionKeyToWizardData(){
        if(wizardData.getStep2_storyFormat().getValue().equals(UpreaderConstants.STORY)){
             System.out.println("Get croco Preview URL " +  wizardData.getStep2_uploadedSampleStory().getCrocoUUID());
             wizardData.setStep6_crocoSessionKeyForSampleOrPilot(getProjectPreviewUrl(wizardData.getStep2_uploadedSampleStory().getCrocoUUID()));
        }
    }

    private AddProjectWizardDTO  getSessionWizardData(){
          if(   context() == null ||
                context().session() == null ||
                context().session().getObject(UpreaderConstants.SESSION_NEWPROJECT_WIZ) == null) {
              return new AddProjectWizardDTO(context());
          }else{
              return (AddProjectWizardDTO) context().session().getObject(UpreaderConstants.SESSION_NEWPROJECT_WIZ);
          }
    }

    public void createAndInsertNewProject(AddProjectWizardDTO theWizardData) throws BusinessException {
        //TODO
        //validate the received data according to the house rules
        try{
            User loggedUser = (User) context().session().getObject(UpreaderConstants.SESSION_USER);
            Project project = new Project();

            //Step 2 data
            project.setTitle(theWizardData.getStep2_storyTitle());
            project.setFormat(theWizardData.getStep2_storyFormat().getValue());
            project.setType(theWizardData.getStep2_storyType().getValue());
            project.setGenre(theWizardData.getStep2_storyGenre().getValue());
            project.setSubgenre(theWizardData.getStep2_storySubGenre().getValue());
            project.setCategory(theWizardData.getStep2_storyCategory().getValue());
            //The pitch
            Joiner joiner = Joiner.on("; ").skipNulls();
            project.setTags( joiner.join(theWizardData.getStep2_tags()) );
            project.setSynopsis(theWizardData.getStep2_storySynopsis());
            //the chapters no
            project.setSerialStoryAvgWordsPerChapter(Integer.valueOf(theWizardData.getStep2_aproxChapterWordCount()));
            project.setSerialStoryUpdateDelay(Integer.valueOf(theWizardData.getStep2_delayBetweenChapterUpdates()));

            //if story and not serial story
            if(theWizardData.getStep2_uploadedSampleStory() != null){
                project.setSample(theWizardData.getStep2_uploadedSampleStory().getKey());
                project.setSampleViewUUID(theWizardData.getStep2_uploadedSampleStory().getCrocoUUID());
            }
            if(theWizardData.getStep2_uploadedStory() != null){
                project.setBook(theWizardData.getStep2_uploadedStory().getKey());
            }

            List<ProjectPost> projectPosts = new ArrayList<ProjectPost>();
            for(String projectPost : theWizardData.getStep2_backstories()){
                ProjectPost newPojectPost = new ProjectPost();
                newPojectPost.setContent(projectPost);
                newPojectPost.setProject(project);
                projectPosts.add(newPojectPost);
            }

            //Step3 Data
            project.setSellingRights(theWizardData.getStep3_yearsOfSellingRightsToPlatform());
            project.setEstimatedUnitSales(theWizardData.getStep3_sellEstimateUnitsPerYear());
            project.setBookPrice(theWizardData.getStep3_ebookPrice().floatValue());
            project.setPercentToPlatform(theWizardData.getStep3_percentRoyaltiesToPlatform());
            project.setMinSharesToBuy(theWizardData.getStep3_numberOfSharesValue()); //target shares to be sold
            project.setSharesToSale(theWizardData.getStep3_numberOfSharesValue()); //the shares will decrease as the upreaders buy in.

            project.setShareValue(theWizardData.getStep3_shareValue().floatValue());
            String derivatives = theWizardData.isStep3_agreeAudioBook() ? "audiobook " : "";
            derivatives += theWizardData.isStep3_agreeMovie() ?  "movie " : "";
            derivatives += theWizardData.isStep3_agreePrint() ?  "print " : "";
            derivatives += theWizardData.isStep3_agreeTV() ?  "tv " : "";
            derivatives += theWizardData.isStep3_agreeUK() ?  "UK " : "";
            project.setDerivatives(derivatives);

            AmazonS3FileDetails[] uploadedProofs = theWizardData.getStep3_uploadedProofDocuments();
            List<ProofOfSales> proofOfSales = new ArrayList<ProofOfSales>();
            for(int i=0; i < uploadedProofs.length; i++){
                ProofOfSales newProofOfSales = new ProofOfSales();
                newProofOfSales.setProject(project);
                newProofOfSales.setProof(uploadedProofs[i].getKey());
                proofOfSales.add(newProofOfSales);
            }

            //Calculated Fields
            project.setSalesProjection(theWizardData.getStep3_ebookPrice()
                                       .multiply(BigDecimal.valueOf(theWizardData.getStep3_sellEstimateUnitsPerYear().doubleValue()))
                                       .multiply(BigDecimal.valueOf(theWizardData.getStep3_yearsOfSellingRightsToPlatform().doubleValue())).intValue()
            );
            project.setIRS(theWizardData.getStep3_ebookPrice()
                    .multiply(BigDecimal.valueOf(theWizardData.getStep3_sellEstimateUnitsPerYear().doubleValue()))
                    .multiply(BigDecimal.valueOf(theWizardData.getStep3_yearsOfSellingRightsToPlatform().doubleValue()))
                    .multiply(BigDecimal.valueOf(theWizardData.getStep3_percentRoyaltiesToPlatform().doubleValue()))
                    .divide(BigDecimal.valueOf(100))
                    .intValue()
            );

            /**
             * Set deadline
             */
            if(0 < project.getIRS() && project.getIRS() < 10000){project.setDeadline(DateTime.now().plusDays(30).toDate());}
            if(10000 <= project.getIRS() && project.getIRS() < 20000){project.setDeadline(DateTime.now().plusDays(45).toDate());}
            if(20000 <= project.getIRS() ){project.setDeadline(DateTime.now().plusDays(60).toDate());}

            float totalSharesExactValue = project.getIRS().floatValue() / project.getShareValue();
            project.setTotalShares(BigDecimal.valueOf(totalSharesExactValue).intValue());

            /**
             * Set initial stock transaction - make all the shares to be sold available in the marketplace
             */
            List<StockTransaction> initialTransactions = new ArrayList<StockTransaction>();
            StockTransaction sellShares = new StockTransaction();
            sellShares.setProject(project);
            sellShares.setSharesNo(theWizardData.getStep3_numberOfSharesValue());
            sellShares.setAmount(project.getShareValue() * theWizardData.getStep3_numberOfSharesValue());
            sellShares.setSeller(loggedUser);
            initialTransactions.add(sellShares);

            //FKs
            project.setAuthor(loggedUser);
            project.setProofsOfSales(proofOfSales);
            project.setApproved(false);
            project.setProjectPosts(projectPosts);

            project.setStockTransactions(initialTransactions);
            context().projectDAO().insert(project);

            //Reinitialize session wizard data
            resetWizardData();
        }catch(Exception e){
            this.wizardData.setStep6_errorMessage(e.getMessage() + "\n" + e.getCause());
            context().session().putObject(UpreaderConstants.SESSION_NEWPROJECT_WIZ, this.wizardData);
            throw new BusinessException(e.getMessage());
        }
    }

    public void resetWizardData(){
        AddProjectWizardDTO defaults = new AddProjectWizardDTO();
        defaults.setWizardDefaults(context());
        this.wizardData = defaults;
        context().session().putObject(UpreaderConstants.SESSION_NEWPROJECT_WIZ, this.wizardData);
    }

    public boolean loadProjectsTableJson(){
        return handler().json(handler().context().projectDAO().findAllProjectsInRange(Integer.valueOf(context().query().get("startPos")).intValue(),
                Integer.valueOf(context().query().get("endPos")).intValue()));
    }

    public String getProjectPreviewUrl(String previewUUID){
        CrocodocResultDTO result = new CrocodocResultDTO();
        try{
            Map<String, Object> status = CrocodocDocument.status(previewUUID);
            String sessionKey = UprCrocodocSession.create(previewUUID);
            result.setSessionKey(sessionKey);
            result.setDocStatus(status.get("status").toString());
            result.setResult(true);
            return sessionKey;
        }catch (CrocodocException ce){
            result.setResult(false);
            return "";
        }
    }

    /*
     * Basic getters and setters
     */

    public AddProjectWizardDTO getWizardData() {
        return wizardData;
    }
}
