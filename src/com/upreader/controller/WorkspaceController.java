package com.upreader.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.upreader.context.Context;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.helper.AddProjectWizardHelper;
import com.upreader.helper.JsonWriter;
import com.upreader.model.Notification;
import com.upreader.model.Project;
import org.apache.log4j.Logger;


import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WorkspaceController extends BasicController{
    Logger log = Logger.getLogger(WorkspaceController.class);

    public WorkspaceController(BasicPathHandler handler, Context context) {
        super(handler, context);
    }

    public boolean doCmd() {
        String cmd = context().query().get("do");
        String userIdParam = context().query().get("userId");
        Integer userId = null;
        try{
            userId = Integer.valueOf(userIdParam);
        }catch(NumberFormatException nfe){
            log.error(nfe);
            return false;
        }

        switch (cmd) {
            case "getNotificationsReceivedByUser":
                return handler().json( retrieveNotifications(userId) );
            case "getUserIncome":
                return handler().json( retrieveUserIncomeData(userId) );
            default:
                return handler().homepage();
        }
    }

    private ArrayList<Map> retrieveNotifications(Integer userId){
        return context().notificationsDAO().getNotificationsReceivedByUser(userId);
    }

    private List<Project>  retrieveUserIncomeData(Integer userId){
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter=new StringWriter();
        ObjectWriter oWriter = mapper.writerWithType(new TypeReference<List<Project>>() {});
        try{

            oWriter.writeValue(strWriter, context().projectDAO().getProjectsIncomeForUser(userId));
            String userDataJSON = strWriter.toString();
            System.out.println(userDataJSON);

        }
            catch(Exception e){

            }
        return context().projectDAO().getProjectsIncomeForUser(userId);
    }
}
