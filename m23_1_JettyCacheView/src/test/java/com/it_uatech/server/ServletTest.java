package com.it_uatech.server;

import com.it_uatech.api.dao.AccountDao;
import com.it_uatech.api.model.AccountDataSet;
import com.it_uatech.api.service.DBServiceAccountImpl;
import com.it_uatech.hibernate_impl.HibernateUtil;
import com.it_uatech.hibernate_impl.dao.AccountDaoImpl;
import com.it_uatech.hibernate_impl.sessionmanager.SessionManagerHibernate;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServletTest {

    private static DBServiceAccountImpl serviceAccount;
    private static AccountDataSet ads1;
    private static AccountDataSet ads2;

    @BeforeAll
    public static void createDataSets(){
        ads1 = new AccountDataSet("maksym67","qwerty");
        ads2 = new AccountDataSet("anna21","qwertdf");
    }

    @BeforeEach
    public void setUp(){
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory("hibernate-test.cfg", AccountDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        AccountDao dao = new AccountDaoImpl(sessionManager);
        serviceAccount = new DBServiceAccountImpl(dao);
    }

    @Test
    public void shouldValidateIfLoginExistsInDB(){
        LoginServlet servlet = new LoginServlet(serviceAccount);
        serviceAccount.save(ads1);
        serviceAccount.save(ads2);

        assertTrue(servlet.validate(ads2.getLogin(),ads2.getPassword()));
    }

    @Test
    public void shouldSaveUserLoginAndPassword(){
        SignUpServlet servletSignUp = new SignUpServlet(serviceAccount);
        LoginServlet servletLogin = new LoginServlet(serviceAccount);

        servletSignUp.saveUser("manya","mycat");

        assertTrue(servletLogin.validate("manya","mycat"));
    }

    @AfterAll
    public static void tearDown(){
        serviceAccount.shutdown();
    }

}