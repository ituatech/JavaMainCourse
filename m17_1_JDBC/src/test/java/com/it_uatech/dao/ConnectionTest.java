package com.it_uatech.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ConnectionTest {

    Connection connection;

    @Before
    public void establishConnection(){
        connection = ConnectionHelper.getMySQLConnection();
    }

    @Test
    public void getMySQLConnection() throws SQLException {
        assertNotNull(connection);
        assertFalse(connection.isClosed());
        assertTrue(connection.isValid(1));
    }

    @After
    public void closeConnection() throws SQLException {
        connection.close();
    }
}