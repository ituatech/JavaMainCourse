package com.it_uatech.connection;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionHelper {

    static Connection getPostgresqlConnection() throws SQLException {
//        final Driver driver = new org.postgresql.Driver();
//        final Driver driver = (Driver) Class.forName("org.postgresql.Driver").getConstructor().newInstance();
//        DriverManager.registerDriver(driver);

        final String url = "jdbc:postgresql://" +           // db type
                "localhost:" +                              // host name
                "5432/" +                                   // port
                "db_example?" +                             // db name
                "user=maksym&" +                            // login
                "password=maksym";                          // password

        return DriverManager.getConnection(url);
    }

    static Connection getMysqlConnection() {
        try {
            //final Driver driver = new com.mysql.cj.jdbc.Driver();
            //final Driver driver = (Driver) Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            //DriverManager.registerDriver(driver);

            String url = "jdbc:mysql://" +         //db type
                    "localhost:" +                 //host name
                    "3306/" +                      //port
                    "db_example?" +                //db name
                    "useSSL=false&"+                //do not use Secure Sockets Layer
                    "allowPublicKeyRetrieval=true&"+
                    "serverTimezone=UTC";
            String user = "maks";
            String password = "maks";
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
