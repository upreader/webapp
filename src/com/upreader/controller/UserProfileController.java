package com.upreader.controller;

import com.upreader.UpreaderApplication;
import com.upreader.UpreaderConstants;
import com.upreader.context.Context;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.dto.UserPublicDataDTO;
import com.upreader.helper.WebHelper;
import com.upreader.model.User;
import org.apache.log4j.Logger;

import java.io.IOException;

public class UserProfileController extends BasicController{
    Logger log = Logger.getLogger(UserProfileController.class);

    public UserProfileController(BasicPathHandler handler, Context context) {
        super(handler, context);
    }

    public boolean doCmd() {
        String cmd = context().query().get("do");

        switch (cmd) {
            case "updateUserProfile":
                return handler().json(updateUserProfileData());
            default:
                return handler().homepage();
        }
    }

    private boolean updateUserProfileData(){
        String userProfileDataParam = context().query().get("userprofiledata");
        UserPublicDataDTO userProfileData = null;
        try{
            User loggedUser = (User) context().session().getObject(UpreaderConstants.SESSION_USER);
            userProfileData =  (UserPublicDataDTO) WebHelper.fromJson(UserPublicDataDTO.class, userProfileDataParam);
            syncUserData(userProfileData, loggedUser);
            context().userDAO().update(loggedUser);
            context().session().putObject(UpreaderConstants.SESSION_USER, loggedUser);
            return true;
        }catch(IOException nfe){
            log.error(nfe);
            return false;
        }
    }

    private void syncUserData(UserPublicDataDTO newUserData, User user){
        user.setFirstName(newUserData.getFirstName());
        user.setLastName(newUserData.getLastName());
        user.setBirthday(newUserData.getBirthDay());
        user.setOccupation(newUserData.getOccupation());
        user.setEmail(newUserData.getEmail());
        user.setRating(newUserData.getRating());
        user.setBio(newUserData.getBio());
        user.setEducation(newUserData.getEducation());
        user.setExperience(newUserData.getExperience());
        user.setMotivation(newUserData.getMotivation());
        user.setInspiration(newUserData.getInspiration());
        user.setEmailConfirmed(newUserData.getEmailConfirmed());
        user.setCity(newUserData.getCity());
        user.setState(newUserData.getState());
        user.setCountry(newUserData.getCountry());
        user.setZip(newUserData.getZip());
        user.setAddress(newUserData.getAddress());
        user.setEmailConfirmDeadline(newUserData.getEmailConfirmationDeadline());
    }
}
