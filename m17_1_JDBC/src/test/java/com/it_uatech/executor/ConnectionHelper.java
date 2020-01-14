package com.it_uatech.executor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionHelper {
   static Connection getMySQLConnection(){
       String url = "jdbc:mysql://" +
               "localhost:" +
               "3306/" +
               "users?" +
               "useSSL=false&"+
               "allowPublicKeyRetrieval=true&"+
               "serverTimezone=Europe/Kiev";
       String user = "myorm";
       String password = "password";
       try {
           return DriverManager.getConnection(url,user,password);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }
}
