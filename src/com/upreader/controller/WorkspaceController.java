package com.upreader.controller;

import com.upreader.context.Context;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.helper.AddProjectWizardHelper;
import com.upreader.model.Notification;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkspaceController extends BasicController{
    Logger log = Logger.getLogger(WorkspaceController.class);

    public WorkspaceController(BasicPathHandler handler, Context context) {
        super(handler, context);
    }

    public boolean doCmd() {
        String cmd = context().query().get("do");
        switch (cmd) {
            case "getNotificationsReceivedByUser":
                String userIdParam = context().query().get("userId");
                Integer userId = null;
                try{
                   userId = Integer.valueOf(userIdParam);
                }catch(NumberFormatException nfe){
                    log.error(nfe);
                    return false;
                }
                return handler().json( retrieveNotifications(userId) );
            default:
                return handler().homepage();
        }
    }

    private ArrayList<Map> retrieveNotifications(Integer userId){
        final ArrayList<Map> notifications = context().notificationsDAO().getNotificationsReceivedByUser(userId);
        return notifications;
    }
}
