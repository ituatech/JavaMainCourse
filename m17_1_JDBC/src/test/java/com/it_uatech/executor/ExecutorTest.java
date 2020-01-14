package com.it_uatech.executor;

import com.it_uatech.exceptions.ORMException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ExecutorTest {


    Connection connection;
    Executor exec;

    private static final String DELETE_PERSONS = "DROP TABLE IF EXISTS persons";
    private static final String CREATE_TABLE_PERSONS = "CREATE TABLE IF NOT EXISTS persons " +
            " (id BIGINT(20) NOT NULL AUTO_INCREMENT, name VARCHAR(255) DEFAULT '', age INT(3) DEFAULT 0, " +
            " PRIMARY KEY (id) ) ENGINE = INNODB DEFAULT CHARSET = utf8;";

    @Before
    public void establishConnection() throws SQLException {
        connection = ConnectionHelper.getMySQLConnection();
        exec = new Executor(connection);
        exec.execUpdate(DELETE_PERSONS);
        exec.execUpdate(CREATE_TABLE_PERSONS);
    }


    @Test
    public void shouldAddNewPersonInTableAndReturnThisPerson() throws SQLException, ORMException {
        final String INSERT_PERSON = "insert into persons (name,age) values ('%s','%d')";
        final String SELECT_PERSON_NAME = "select name from persons";
        final String SELECT_PERSON_AGE = "select age from persons";

        int rows = exec.execUpdate(String.format(INSERT_PERSON, "Anna",43));
        assertEquals(1,rows);

        String resultName = exec.execQuery(SELECT_PERSON_NAME, resultSet -> {
            resultSet.next();
            String name = resultSet.getString("name");
            return name;
        });
        assertEquals("Anna",resultName);

        int resultAge = exec.execQuery(SELECT_PERSON_AGE, resultSet -> {
            resultSet.next();
            int age = resultSet.getInt("age");
            return age;
        });
        assertEquals(43,resultAge);

    }

    @Test
    public void shouldGenerateAndReturnNewIdByPersonAdding() throws SQLException {
        final String INSERT_PERSON = "insert into persons (name,age) values ('%s','%d')";

        long id = exec.execUpdateWithReturnGeneratedKeys(String.format(INSERT_PERSON, "Anna",43));
        assertEquals(1,id);

        id = exec.execUpdateWithReturnGeneratedKeys(String.format(INSERT_PERSON, "Maksym",25));
        assertEquals(2,id);
    }

    @Test
    public void shouldAddNewPersonWithPreparedStatement() throws SQLException, ORMException {
        final String INSERT_INTO_PERSONS = "insert into persons (name,age) values(?,?)";
        final String SELECT_PERSON_NAME = "select name from persons";

        exec.execUpdatePrepared(INSERT_INTO_PERSONS,preStmt->{
            preStmt.setString(1,"Zoriana");
            preStmt.setInt(2,3);
            preStmt.execute();

            preStmt.setString(1,"Maksym");
            preStmt.setInt(2,24);
            preStmt.execute();
        });

        exec.execQuery(SELECT_PERSON_NAME, resultSet -> {
            resultSet.next();
            assertEquals("Zoriana",resultSet.getString("name"));
            resultSet.next();
            assertEquals("Maksym",resultSet.getString("name"));
            return null;
        });
    }

    @Test
    public void shouldGenerateAndReturnNewIdByPersonAddingWithPreparedStatement() throws SQLException {
        final String INSERT_INTO_PERSONS = "insert into persons (name,age) values(?,?)";

        exec.execUpdatePreparedWithReturnGeneratedKeys(INSERT_INTO_PERSONS,preStmt->{
            preStmt.setString(1,"Zoriana");
            preStmt.setInt(2,3);
            preStmt.execute();
            ResultSet rs1 = preStmt.getGeneratedKeys();
            rs1.next();
            assertEquals(1,rs1.getLong(1));

            preStmt.setString(1,"Maksym");
            preStmt.setInt(2,24);
            preStmt.execute();
            ResultSet rs2 = preStmt.getGeneratedKeys();
            rs2.next();
            assertEquals(2,rs2.getLong(1));
        });
    }

    @Test
    public void shouldReturnPersonById() throws SQLException, ORMException {
        final String SELECT_PERSON = "select name from persons where id=%d";
        final String INSERT_INTO_PERSONS = "insert into persons (name,age) values(?,?)";

        exec.execUpdatePrepared(INSERT_INTO_PERSONS,preStmt->{
            preStmt.setString(1,"Zoriana");
            preStmt.setInt(2,3);
            preStmt.execute();

            preStmt.setString(1,"Maksym");
            preStmt.setInt(2,24);
            preStmt.execute();

            preStmt.setString(1,"Anna");
            preStmt.setInt(2,43);
            preStmt.execute();
        });

        exec.execQuery(String.format(SELECT_PERSON,1),resultSet -> {
            resultSet.next();
            assertEquals("Zoriana",resultSet.getString(1));
            return null;
        });

        exec.execQuery(String.format(SELECT_PERSON,2),resultSet -> {
            resultSet.next();
            assertEquals("Maksym",resultSet.getString(1));
            return null;
        });

        exec.execQuery(String.format(SELECT_PERSON,3),resultSet -> {
            resultSet.next();
            assertEquals("Anna",resultSet.getString(1));
            return null;
        });
    }

    @Test
    public void shouldNotGenerateNewId() throws SQLException {
        long id = exec.execUpdateWithReturnGeneratedKeys(CREATE_TABLE_PERSONS);
        assertEquals(-1,id);
    }


    @After
    public void closeConnection() throws SQLException {
        connection.close();
    }
}