package com.it_uatech.application;

import com.it_uatech.api.cache.CacheEngine;
import com.it_uatech.api.cache.CacheEngineImpl;
import com.it_uatech.api.dao.AccountDao;
import com.it_uatech.api.dao.UserDao;
import com.it_uatech.api.model.AccountDataSet;
import com.it_uatech.api.model.AddressDataSet;
import com.it_uatech.api.model.PhoneDataSet;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.service.DBCachedServiceUserImpl;
import com.it_uatech.api.service.DBServiceAccount;
import com.it_uatech.api.service.DBServiceAccountImpl;
import com.it_uatech.api.service.DBServiceUser;
import com.it_uatech.hibernate_impl.HibernateUtil;
import com.it_uatech.hibernate_impl.dao.AccountDaoImpl;
import com.it_uatech.hibernate_impl.dao.UserDaoImpl;
import com.it_uatech.hibernate_impl.sessionmanager.SessionManagerHibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean(name = "sessionManagerHibernate")
    SessionManagerHibernate getSessionManagerHibernate(){
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory("hibernate.cfg", AccountDataSet.class, UserDataSet.class, AddressDataSet.class, PhoneDataSet.class);
        return new SessionManagerHibernate(sessionFactory);
    }

    @Bean(name = "dbServiceAccount")
    DBServiceAccount getDBServiceAccount(SessionManagerHibernate sessionManagerHibernate){
        AccountDao dao = new AccountDaoImpl(sessionManagerHibernate);
        return new DBServiceAccountImpl(dao);
    }

    @Bean(name = "cache")
    CacheEngine<Long, UserDataSet> getCache(@Value("${cache.size}")int size, @Value("${cache.lifeTime}")long lifeTime, @Value("${cache.idleTime}")long idleTime){
        return new CacheEngineImpl<>(size,lifeTime,idleTime);
    }

    @Bean(name = "dbServiceUser")
    DBServiceUser getDBServiceUser(SessionManagerHibernate sessionManagerHibernate, CacheEngine<Long, UserDataSet> cache){
        UserDao dao = new UserDaoImpl(sessionManagerHibernate);
        return new DBCachedServiceUserImpl(dao,cache);
    }

    @Bean(name = "generator")
    Runnable getUserSetGenerator(DBServiceUser serviceUser){
        return new UserSetGenerator(serviceUser);
    }

}
