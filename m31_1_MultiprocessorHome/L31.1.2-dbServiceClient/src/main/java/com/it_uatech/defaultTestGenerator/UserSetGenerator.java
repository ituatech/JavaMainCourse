package com.it_uatech.defaultTestGenerator;

import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.service.DBServiceUser;

public class UserSetGenerator implements Runnable {

    private DBServiceUser serviceUser;

    public UserSetGenerator(DBServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 300){
            UserDataSet uds1 = new UserDataSet("kolyan"+Math.random()*10, (int)(Math.random()*100));
            UserDataSet uds2 = new UserDataSet("mikola"+Math.random()*10, (int)(Math.random()*100));

            long id = serviceUser.save(uds1);
            serviceUser.save(uds2);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            serviceUser.findById((long) (Math.random()*id));
        }
    }
}
