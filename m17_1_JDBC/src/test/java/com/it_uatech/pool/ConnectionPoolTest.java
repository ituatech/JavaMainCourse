package com.it_uatech.pool;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ConnectionPoolTest {

    ConnectionPool connectionPool;

    @Before
    public void setUp(){
        connectionPool = new ConnectionPool(new JDBCConnection());
    }

    @Test
    public void shouldCreateConnections() throws SQLException {
        Connection conn1 = connectionPool.get();
        Connection conn2 = connectionPool.get();
        assertTrue(conn1.isValid(1));
        assertTrue(conn2.isValid(1));
    }

    @Test
    public void shouldReturnConnectionsInPool() throws SQLException {
        Connection conn1 = connectionPool.get();
        Connection conn2 = connectionPool.get();

        assertEquals(0,connectionPool.poolSize());
        conn1.close();
        conn2.close();

        assertEquals(2,connectionPool.poolSize());
    }

    @Test
    public void shouldDisposeAllConnections() throws SQLException {
        Connection conn1 = connectionPool.get();
        Connection conn2 = connectionPool.get();

        conn1.close();
        conn2.close();
        assertEquals(2,connectionPool.poolSize());
        connectionPool.dispose();
        assertEquals(0,connectionPool.poolSize());
    }

    @After
    public void disposePool() throws SQLException {
        connectionPool.dispose();
    }


}