package com.upreader.controller;

import com.upreader.context.Context;
import com.upreader.dispatcher.BasicPathHandler;
import org.apache.log4j.Logger;

public class LibraryController extends BasicController{
    Logger log = Logger.getLogger(WorkspaceController.class);

    public LibraryController(BasicPathHandler handler, Context context) {
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
                return true;
            default:
                return handler().homepage();
        }
    }
}
