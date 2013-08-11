package com.upreader.helper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.upreader.BasicExceptionHandler;
import com.upreader.UpreaderConstants;
import com.upreader.context.Context;
import com.upreader.controller.BasicController;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.dto.AddProjectWizardDTO;
import com.upreader.model.Project;
import com.upreader.model.ProjectOwnership;
import com.upreader.model.User;

import java.io.IOException;

import static com.google.common.base.Strings.emptyToNull;



public class AddProjectWizardHelper extends BasicController{
    private AddProjectWizardDTO wizardData;

    public AddProjectWizardHelper(BasicPathHandler handler, Context context){
            super(handler,context);
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

        ProjectOwnership projectOwnership = new ProjectOwnership();
        projectOwnership.setProject(project);
        projectOwnership.setUser((User) context().session().getObject("_user_"));

        project.setBookPrice(theWizardData.getStep3_ebookPrice().floatValue());
        project.setAuthor(projectOwnership);
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
