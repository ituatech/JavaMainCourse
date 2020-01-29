package com.it_uatech.main;

import com.it_uatech.base.DBService;
import com.it_uatech.base.dataSets.PhoneDataSet;
import com.it_uatech.base.dataSets.UserDataSet;
import com.it_uatech.dbService.DBServiceImpl;

import java.util.List;

/**
 * mysql> CREATE USER 'myorm'@'localhost' IDENTIFIED BY 'myorm';
 * mysql> GRANT ALL PRIVILEGES ON * . * TO 'myorm'@'localhost';
 * mysql> select user, host from mysql.user;
 * mysql> create database users;
 * mysql> SET GLOBAL time_zone = '+2:00';
 */

public class Main {
    public static void main(String[] args) {
        DBService dbService = new DBServiceImpl();

        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        dbService.save(new UserDataSet("tully", new PhoneDataSet("12345")));
        dbService.save(new UserDataSet("sully", new PhoneDataSet("67890")));

        UserDataSet dataSet = dbService.read(1);
        System.out.println(dataSet);

        dataSet = dbService.readByName("sully");
        System.out.println(dataSet);

        List<UserDataSet> dataSets = dbService.readAll();
        for (UserDataSet userDataSet : dataSets) {
            System.out.println(userDataSet);
        }

        dbService.shutdown();

    }

}
