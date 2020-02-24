package com.it_uatech.api.service;

import com.it_uatech.api.cache.CacheEngine;
import com.it_uatech.api.cache.CacheEngineImpl;
import com.it_uatech.api.dao.UserDao;
import com.it_uatech.api.model.AddressDataSet;
import com.it_uatech.api.model.PhoneDataSet;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.hibernate_impl.HibernateUtil;
import com.it_uatech.hibernate_impl.dao.UserDaoImpl;
import com.it_uatech.hibernate_impl.sessionmanager.SessionManagerHibernate;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class DBCachedServiceUserImplTest {

    private DBCachedServiceUserImpl serviceUser;
    private CacheEngine<Long,UserDataSet> cache;
    private UserDataSet uds1;
    private UserDataSet uds2;
    private UserDataSet uds3;
    private UserDataSet uds4;
    private long lifeTime = 5000;

    @BeforeEach
    public void createDataSets(){
        uds1 = new UserDataSet("Maks",25);
        uds2 = new UserDataSet("Anna",42);
        uds3 = new UserDataSet("Zorya",3);
        uds4 = new UserDataSet("Papa",33);

        cache = new CacheEngineImpl<>(5,lifeTime,0);
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory("hibernate-test.cfg", UserDataSet.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao dao = new UserDaoImpl(sessionManager);
        serviceUser = new DBCachedServiceUserImpl(dao,cache);
    }

    @Test
    public void shouldSaveUserDataSetInDatabaseAndInCache(){
        long id1 = serviceUser.save(uds1);
        long id2 = serviceUser.save(uds2);

        assertNotNull(cache.get(id1));
        assertNotNull(cache.get(id2));
    }

    @Test
    public void shouldRetrieveHitsAndMiss() throws InterruptedException {
        long id1 = serviceUser.save(uds3);
        long id2 = serviceUser.save(uds4);
        cache.get(id1);
        cache.get(id2);
        cache.get(id1);
        cache.get(id2);
        assertEquals(4,cache.getHitCount());
        assertEquals(0,cache.getMissCount());

        Thread.sleep(lifeTime+lifeTime/2);
        cache.get(id1);
        cache.get(id2);
        assertEquals(2,cache.getMissCount());
    }

    @AfterEach
    public void tearDown(){
        serviceUser.shutdown();
        cache.dispose();
    }

}