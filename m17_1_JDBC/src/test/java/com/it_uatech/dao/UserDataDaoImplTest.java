package com.it_uatech.dao;

import com.it_uatech.annotations.Column;
import com.it_uatech.annotations.Table;
import com.it_uatech.entity.DataSet;
import com.it_uatech.entity.UserDataSet;
import com.it_uatech.exceptions.ORMException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class UserDataDaoImplTest {

    UserDataDaoImpl userDataDao;
    UserDataSet uds;

    @Before
    public void establishConnection(){
        userDataDao = new UserDataDaoImpl();
        uds = new UserDataSet("anna",56);
    }

    @Test
    public void close() throws SQLException {
        assertFalse(userDataDao.getConnection().isClosed());
        userDataDao.close();
        assertTrue(userDataDao.getConnection().isClosed());
    }

    @Test
    public void shouldDeleteExistTable() throws SQLException {
        userDataDao.deleteTables();
        boolean rows = userDataDao.getConnection().getMetaData().getTables(null,null,"persons",null).next();
        assertFalse(rows);
    }

    @Test
    public void shouldCreateNewTable() throws SQLException {
        userDataDao.createTables();
        boolean rows = userDataDao.getConnection().getMetaData().getTables(null,null,"persons",null).next();
        assertTrue(rows);
    }

    @Test
    public void shouldReturnListOfFieldsWithColumnAnnotationFromTableType() throws ORMException {
        List<Field> list = userDataDao.getORMFields(uds.getClass());
        assertEquals("name",list.get(0).getAnnotation(Column.class).name());
        assertEquals("age",list.get(1).getAnnotation(Column.class).name());
    }

    @Test
    public void shouldBuildInsertIntoSQLStatement() throws ORMException {
        String sqlStatement = userDataDao.buildInsertIntoSQLStatement(uds);
        assertEquals("insert into persons (name, age) values ('anna', '56')",sqlStatement);
    }

    @Test
    public void shouldBuildInsertIntoSQLStatementAlsoIfFieldIsNull() throws ORMException {
        UserDataSet userDataSet = new UserDataSet(null,98);
        String sqlStatement = userDataDao.buildInsertIntoSQLStatement(userDataSet);
        assertEquals("insert into persons (name, age) values ('', '98')",sqlStatement);
    }

    @Test
    public void shouldSavePersonInDBAndReturnItBack() throws SQLException, ORMException {
        userDataDao.deleteTables();
        userDataDao.createTables();

        userDataDao.save(uds);
        Optional<UserDataSet> udsDB = userDataDao.loadUsingClassConstructor(1,UserDataSet.class);
        assertEquals(uds,udsDB.get());

        udsDB = userDataDao.loadUsingORM(1,UserDataSet.class);
        assertEquals(uds,udsDB.get());
    }

    @Test
    public void shouldReturnListOfPersonsFromDB() throws SQLException, ORMException {
        userDataDao.deleteTables();
        userDataDao.createTables();

        UserDataSet uds1 = new UserDataSet("Maksym", 25);
        UserDataSet uds2 = new UserDataSet("Anna",99);
        UserDataSet uds3 = new UserDataSet("Zoriana",3);

        userDataDao.save(uds1);
        userDataDao.save(uds2);
        userDataDao.save(uds3);

        List<UserDataSet> users = userDataDao.loadAll(UserDataSet.class);

        assertEquals(uds1,users.get(0));
        assertEquals(uds2,users.get(1));
        assertEquals(uds3,users.get(2));
    }

    @Test(expected = ORMException.class)
    public void shouldTrowExceptionByNotORMTableObject() throws ORMException {
        ObjectWithoutTableAnnotation owta = new ObjectWithoutTableAnnotation("anna",62);
        userDataDao.getORMFields(owta.getClass());
    }

    @Test(expected = ORMException.class)
    public void shouldTrowExceptionByNoColumnsInORMTableObject() throws ORMException {
        ObjectWithoutColumnAnnotation owta = new ObjectWithoutColumnAnnotation("anna",62);
        userDataDao.getORMFields(owta.getClass());
    }

    @After
    public void closeConnection() throws SQLException {
        userDataDao.close();
    }

    static class ObjectWithoutTableAnnotation extends DataSet{
        @Column(name = "name")
        private String name;
        @Column(name = "age")
        private int age;

        public ObjectWithoutTableAnnotation(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    @Table(name = "persons")
    static class ObjectWithoutColumnAnnotation extends DataSet {

        private String name;
        private int age;

        public ObjectWithoutColumnAnnotation(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

}