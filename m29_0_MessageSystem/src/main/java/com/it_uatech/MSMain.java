package com.it_uatech;


import com.it_uatech.app.DBService;
import com.it_uatech.app.FrontendService;
import com.it_uatech.app.MessageSystemContext;
import com.it_uatech.db.DBServiceImpl;
import com.it_uatech.front.FrontendServiceImpl;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.MessageSystem;

public class MSMain {
    public static void main(String[] args) throws InterruptedException {
        MessageSystem messageSystem = new MessageSystem();

        MessageSystemContext context = new MessageSystemContext(messageSystem);
        Address frontAddress = new Address("Frontend");
        context.setFrontAddress(frontAddress);
        Address dbAddress = new Address("DB");
        context.setDbAddress(dbAddress);

        FrontendService frontendService = new FrontendServiceImpl(context, frontAddress);
        frontendService.init();

        DBService dbService = new DBServiceImpl(context, dbAddress);
        dbService.init();

        messageSystem.start();

        int i = 1000;
        while (i>0) {

            //for test
            frontendService.handleRequest("tully");
            frontendService.handleRequest("sully");

            frontendService.handleRequest("tully");
            frontendService.handleRequest("sully");
            i--;
            Thread.sleep(1000);
        }
        messageSystem.dispose();
    }
}
