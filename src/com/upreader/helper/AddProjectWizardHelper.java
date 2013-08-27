package com.upreader.helper;

import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.transfer.Upload;
import com.google.common.base.Optional;
import com.upreader.UpreaderConstants;
import com.upreader.aws.AmazonService;
import com.upreader.context.Context;
import com.upreader.controller.BasicController;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.dto.AddProjectWizardDTO;
import com.upreader.dto.AmazonS3FileDetails;
import com.upreader.dto.AmazonS3FileDetailsBuilder;
import com.upreader.model.Project;
import com.upreader.model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import java.io.*;

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
      User loggedUser    = context().session().getObject(UpreaderConstants.SESSION_USER);
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
        //TODO
        //prefix += loggedUser.getEmail() + "/";
        prefix += "upreader@upreader.com/";

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
        createAndInsertNewProject(wizardData);

        return true;
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

    public void createAndInsertNewProject(AddProjectWizardDTO theWizardData){
        Project project = new Project();
        project.setTitle(theWizardData.getStep2_storyTitle());
        project.setGenre(theWizardData.getStep2_storyGenre());
        project.setSubgenre(theWizardData.getStep2_storySubGenre());
        project.setSynopsis(theWizardData.getStep2_storySynopsis());
        //project.setBook();
        //project.setCover(coverUploadFile);

        project.setBookPrice(theWizardData.getStep3_ebookPrice().floatValue());
        project.setAuthor((User) context().session().getObject("_user_"));
        project.setApproved(false);

        context().projectDAO().insert(project);
    }
    /*
     * Basic getters and setters
     */

    public AddProjectWizardDTO getWizardData() {
        return wizardData;
    }
}
