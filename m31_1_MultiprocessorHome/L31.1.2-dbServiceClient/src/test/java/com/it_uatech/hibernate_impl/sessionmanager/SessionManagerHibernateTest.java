package com.it_uatech.hibernate_impl.sessionmanager;


import com.it_uatech.api.model.AddressDataSet;
import com.it_uatech.api.model.PhoneDataSet;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.session.SessionManager;
import com.it_uatech.api.session.SessionManagerException;
import com.it_uatech.hibernate_impl.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SessionManagerHibernateTest {

    private static SessionFactory sessionFactory;
    private static SessionManager sessionManager;

    @BeforeAll
    public static void setUp(){
        sessionFactory = HibernateUtil.buildSessionFactory("hibernate-test.cfg", UserDataSet.class, AddressDataSet.class, PhoneDataSet.class);
        sessionManager = new SessionManagerHibernate(sessionFactory);
    }

    @Test
    public void shouldGenerateExceptionIfSessionFactoryNull(){
        assertThrows(SessionManagerException.class,()->new SessionManagerHibernate(null),"SessionFactory is null");
    }

    @Test
    public void shouldBeginDatabaseSessionAndOpenTransactionThenCommitTransactionAndCloseSession(){
        sessionManager.beginSession();
        assertTrue(((DataBaseSessionHibernate)sessionManager.getCurrentSession()).getSession().isConnected());
        assertTrue(((DataBaseSessionHibernate)sessionManager.getCurrentSession()).getTransaction().isActive());
        sessionManager.commitSession();
        assertFalse(((SessionManagerHibernate)sessionManager).getHibernateSession().getTransaction().isActive());
        assertFalse(((SessionManagerHibernate)sessionManager).getHibernateSession().getSession().isConnected());
    }

    @Test
    public void shouldRollbackTransactionAndCloseSession(){
        sessionManager.beginSession();
        sessionManager.rollbackSession();
        assertFalse(((SessionManagerHibernate)sessionManager).getHibernateSession().getTransaction().isActive());
        assertFalse(((SessionManagerHibernate)sessionManager).getHibernateSession().getSession().isConnected());
    }

    @Test
    public void shouldCloseDatabaseSession() {
        sessionManager.beginSession();
        assertNotNull(sessionManager.getCurrentSession());
        sessionManager.close();
        assertNull(((SessionManagerHibernate)sessionManager).getHibernateSession());
    }

    @AfterAll
    public static void close() {
        sessionFactory.close();
        sessionManager.close();
    }
}