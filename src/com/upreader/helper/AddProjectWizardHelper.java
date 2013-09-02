package com.upreader.helper;

import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.transfer.Upload;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.upreader.BusinessException;
import com.upreader.UpreaderConstants;
import com.upreader.aws.AmazonService;
import com.upreader.context.Context;
import com.upreader.controller.BasicController;
import com.upreader.controller.UserController;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.dto.AddProjectWizardDTO;
import com.upreader.dto.AmazonS3FileDetails;
import com.upreader.dto.AmazonS3FileDetailsBuilder;
import com.upreader.model.Project;
import com.upreader.model.ProofOfSales;
import com.upreader.model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Strings.emptyToNull;



public class AddProjectWizardHelper extends BasicController{
    private AddProjectWizardDTO wizardData;
    private Logger log = Logger.getLogger(AddProjectWizardHelper.class);

    public AddProjectWizardHelper(BasicPathHandler handler, Context context){
            super(handler,context);
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

      String fType       = context().request().getParameter("fileType");
      User loggedUser = (User) context().session().getObject(UpreaderConstants.SESSION_USER);
      if(fType == null){
          return !handler().serverError("Inconsistent File Type details.").isEmpty();
      }

      //uploading file
      try{
        //Read the file from request.
        final FileItem uploadedFile = context().request().getPart("file");

        if(fType.equals(UpreaderConstants.PUBLIC_IMAGE)){
            isPublic = true;
            prefix = "images/";
        }
        if(fType.equals(UpreaderConstants.STORY)){
            isPublic = false;
            prefix = "stories/";
        }
        if(fType.equals(UpreaderConstants.STORY_SAMPLE)){
            isPublic = false;
            prefix = "samples/";
        }
        if(fType.equals(UpreaderConstants.PROOF_DOCUMENT)){
            isPublic = false;
            prefix = "proofDocuments/";
        }
        prefix += loggedUser.getEmail() + "/";

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
                                                                      .build());
      }catch(Exception e){
          log.error(e);
          return !handler().serverError(e.getMessage()).isEmpty();
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

                //Sync session data
                context().session().putObject(UpreaderConstants.SESSION_NEWPROJECT_WIZ, wizardData);
            } catch (IOException e) {
                context().getDispatcher().dispatchException(context(), e);
            }
        }
        return true;
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
            }
            if(theWizardData.getStep2_uploadedStory() != null){
                project.setBook(theWizardData.getStep2_uploadedStory().getKey());
            }

            //Step3 Data
            project.setSellingRights(theWizardData.getStep3_yearsOfSellingRightsToPlatform());
            project.setEstimatedUnitSales(theWizardData.getStep3_sellEstimateUnitsPerYear());
            project.setBookPrice(theWizardData.getStep3_ebookPrice().floatValue());
            project.setPercentToPlatform(theWizardData.getStep3_percentRoyaltiesToPlatform());
            project.setSharesToSale(theWizardData.getStep3_numberOfSharesValue());
            project.setTotalShares(theWizardData.getStep3_totalNumberOfShares());
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
            //FKs
            project.setAuthor(loggedUser);
            project.setProofsOfSales(proofOfSales);
            project.setApproved(false);

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
    /*
     * Basic getters and setters
     */

    public AddProjectWizardDTO getWizardData() {
        return wizardData;
    }
}
