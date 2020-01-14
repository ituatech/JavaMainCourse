package com.it_uatech.executor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExecutorCreateDeleteTableTest {
    Connection connection;
    Executor exec;

    private static final String DELETE_PERSONS = "DROP TABLE IF EXISTS persons";
    private static final String CREATE_TABLE_PERSONS = "CREATE TABLE IF NOT EXISTS persons " +
            " (id BIGINT(20) NOT NULL AUTO_INCREMENT, name VARCHAR(255) DEFAULT '', age INT(3) DEFAULT 0, " +
            " PRIMARY KEY (id) ) ENGINE = INNODB DEFAULT CHARSET = utf8;";

    @Before
    public void establishConnection() {
        connection = ConnectionHelper.getMySQLConnection();
        exec = new Executor(connection);
    }

    @Test
    public void shouldDeleteExistTable() throws SQLException {
        exec.execUpdate(CREATE_TABLE_PERSONS);
        exec.execUpdate(DELETE_PERSONS);
        boolean rows = exec.getConnection().getMetaData().getTables(null,null,"persons",null).next();
        assertFalse(rows);
    }

    @Test
    public void shouldCreateNewTable() throws SQLException {
        exec.execUpdate(DELETE_PERSONS);
        exec.execUpdate(CREATE_TABLE_PERSONS);
        boolean rows = exec.getConnection().getMetaData().getTables(null,null,"persons",null).next();
        assertTrue(rows);
    }

    @After
    public void closeConnection() throws SQLException {
        connection.close();
    }
}
