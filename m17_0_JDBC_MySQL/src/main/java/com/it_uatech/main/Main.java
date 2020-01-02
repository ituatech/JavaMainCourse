package com.it_uatech.main;

import com.it_uatech.base.DBService;
import com.it_uatech.base.UsersDataSet;
import com.it_uatech.connection.DBServiceConnection;
import com.it_uatech.connection.DBServiceUpdate;
import com.it_uatech.logger.DBServiceLog;
import com.it_uatech.prepared.DBServicePrepared;
import com.it_uatech.simple.DBServiceSimple;
import com.it_uatech.transaction.DBServicePreparedTransactional;

import java.util.List;

/**
 * mysql> CREATE USER 'maks'@'localhost' IDENTIFIED BY 'maks';
 * mysql> GRANT ALL PRIVILEGES ON * . * TO 'maks'@'localhost';
 * mysql> select user, host from mysql.user;
 * mysql> create database db_example;
 * mysql> SET GLOBAL time_zone = '+2:00';
 */

public class Main {
    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        //try(DBService dbService = new DBServiceConnection()) {
        //try (DBService dbService = new DBServiceUpdate()) {
        //try (DBService dbService = new DBServiceLog()) {
        //try (DBService dbService = new DBServiceSimple()) {
        try (DBService dbService = new DBServicePrepared()) {
        //try (DBService dbService = new DBServicePreparedTransactional()) {
            System.out.println(dbService.getMetaData());
            dbService.prepareTables();
            dbService.addUsers("tully", "sully");
            int id = 2;
            System.out.println(String.format("UserName with id = %d : %s", id, dbService.getUserName(id)));
            List<String> names = dbService.getAllNames();
            System.out.println("All names: " + names.toString());
            List<UsersDataSet> users = dbService.getAllUsers();
            System.out.println("All users: " + users.toString());
            //dbService.deleteTables();
        }
    }
}
