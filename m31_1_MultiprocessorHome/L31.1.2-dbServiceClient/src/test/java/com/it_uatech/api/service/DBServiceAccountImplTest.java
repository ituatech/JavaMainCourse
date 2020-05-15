package com.it_uatech.api.service;

import com.it_uatech.api.dao.AccountDao;
import com.it_uatech.api.model.AccountDataSet;
import com.it_uatech.hibernate_impl.HibernateUtil;
import com.it_uatech.hibernate_impl.dao.AccountDaoImpl;
import com.it_uatech.hibernate_impl.sessionmanager.SessionManagerHibernate;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DBServiceAccountImplTest {

    private static DBServiceAccountImpl serviceAccount;
    private static AccountDataSet ads1;
    private static AccountDataSet ads2;

    @BeforeAll
    public static void createDataSets(){
        ads1 = new AccountDataSet("vasya","piterskiy");
        ads2 = new AccountDataSet("kolya","moskovskiy");
    }

    @BeforeEach
    public void setUp(){
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory("hibernate-test.cfg", AccountDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        AccountDao dao = new AccountDaoImpl(sessionManager);
        serviceAccount = new DBServiceAccountImpl(dao);
    }

    @Test
    public void shouldSaveUserLoginAndPasswordRetrieveIt(){
        serviceAccount.save(ads1);
        serviceAccount.save(ads2);

       assertEquals(serviceAccount.findByLogin("vasya").get(),ads1);
        assertEquals(serviceAccount.findByLogin("kolya").get(),ads2);
    }

    @AfterAll
    public static void tearDown(){
        serviceAccount.shutdown();
    }

}