package com.it_uatech.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class JDBCConnection implements ConnectionFactory{
    private String url;
    private String user;
    private String password;

    JDBCConnection(){
        url = "jdbc:mysql://" +
                "localhost:" +
                "3306/" +
                "users?" +
                "useSSL=false&"+
                "allowPublicKeyRetrieval=true&"+
                "serverTimezone=Europe/Kiev";
        user = "myorm";
        password = "password";
    }

    @Override
    public void dispose() {

    }

    @Override
    public Connection get() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
}
