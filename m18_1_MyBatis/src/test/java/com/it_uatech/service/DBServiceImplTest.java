package com.it_uatech.service;

import com.it_uatech.connection.Connection;
import com.it_uatech.entity.UserDataSet;
import com.it_uatech.mapper.UserDAO;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class DBServiceImplTest {

    DBService service;

    @Before
    public void setUp(){
        SqlSessionFactory sqlSessionFactory = Connection.getSqlSessionFactory(Connection.getMySQLDataSource(), UserDAO.class);
        service = new DBServiceImpl(sqlSessionFactory);
    }

    @Test
    public void shouldCreateTable() throws SQLException {
        service.dropTable();
        service.createTable();
        ResultSet rs = ((DBServiceImpl)service).getSqlSessionFactory().getConfiguration().getEnvironment().getDataSource().getConnection().getMetaData().getTables(null,null,"persons",null);
        assertTrue(rs.next());
    }

    @Test
    public void shouldDropTable() throws SQLException {
        service.createTable();
        service.dropTable();
        ResultSet rs = ((DBServiceImpl)service).getSqlSessionFactory().getConfiguration().getEnvironment().getDataSource().getConnection().getMetaData().getTables(null,null,"persons",null);
        assertFalse(rs.next());
    }

    @Test
    public void shouldSaveUser(){
        service.dropTable();
        service.createTable();
        UserDataSet uds1 = new UserDataSet("anna",78);
        int ret = service.save(uds1);
        assertEquals(1, ret);
        assertEquals(1,uds1.getId());

        UserDataSet uds2 = new UserDataSet("maksim",25);
        ret = service.save(uds2);
        assertEquals(1, ret);
        assertEquals(2,uds2.getId());
    }

    @Test
    public void shouldSelectUserById(){
        service.dropTable();
        service.createTable();
        UserDataSet uds1 = new UserDataSet("anna",78);
        UserDataSet uds2 = new UserDataSet("maksim",25);
        UserDataSet uds3 = new UserDataSet("zorya",3);
        service.save(uds1);
        service.save(uds2);
        service.save(uds3);

        assertEquals(uds1,service.select(1));
        assertEquals(uds2,service.select(2));
        assertEquals(uds3,service.select(3));
        UserDataSet uds4 = service.select(4);
        assertNull(uds4);
    }

    @Test
    public void shouldFindUserByAgeAndName(){
        service.dropTable();
        service.createTable();
        UserDataSet uds1 = new UserDataSet("zorya",3);
        UserDataSet uds2 = new UserDataSet("maksim",25);
        UserDataSet uds3 = new UserDataSet("zorya",9);
        service.save(uds1);
        service.save(uds2);
        service.save(uds3);

        assertEquals(uds3,service.findByAgeName("zorya",9));
    }

    @Test
    public void shouldReturnListOfUsers(){
        service.dropTable();
        service.createTable();
        UserDataSet uds1 = new UserDataSet("anna",78);
        UserDataSet uds2 = new UserDataSet("maksim",25);
        UserDataSet uds3 = new UserDataSet("zorya",3);
        service.save(uds1);
        service.save(uds2);
        service.save(uds3);

        List<UserDataSet> udsList = service.selectAll();

        assertEquals(uds1,udsList.get(0));
        assertEquals(uds2,udsList.get(1));
        assertEquals(uds3,udsList.get(2));
    }
}