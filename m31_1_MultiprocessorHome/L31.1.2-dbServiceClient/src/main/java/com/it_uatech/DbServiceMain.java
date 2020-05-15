package com.it_uatech;

import com.it_uatech.api.cache.CacheEngine;
import com.it_uatech.api.cache.CacheEngineImpl;
import com.it_uatech.api.model.AccountDataSet;
import com.it_uatech.api.model.AddressDataSet;
import com.it_uatech.api.model.PhoneDataSet;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.service.DBCachedServiceUserImpl;
import com.it_uatech.api.service.DBServiceAccountImpl;
import com.it_uatech.api.service.DBServiceUser;
import com.it_uatech.app.DBServiceAccountMS;
import com.it_uatech.app.DBServiceUserMS;
import com.it_uatech.channel.SocketMessageTransferClient;
import com.it_uatech.defaultTestGenerator.UserSetGenerator;
import com.it_uatech.hibernate_impl.HibernateUtil;
import com.it_uatech.hibernate_impl.dao.AccountDaoImpl;
import com.it_uatech.hibernate_impl.dao.UserDaoImpl;
import com.it_uatech.hibernate_impl.sessionmanager.SessionManagerHibernate;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DbServiceMain {

    private static final Logger logger = LoggerFactory.getLogger(DbServiceMain.class);

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private int size;
    private int lifeTime;
    private int idleTime;
    private String host;
    private int port;

    public static void main(String[] args) {
        try {
            new DbServiceMain().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() throws IOException {
        initialProperty();
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory("hibernate.cfg", AccountDataSet.class, UserDataSet.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);

        DBServiceAccountMS dbServiceAccount = new DBServiceAccountImpl(new AccountDaoImpl(sessionManagerHibernate));

        CacheEngine<Long, UserDataSet> cache = new CacheEngineImpl<>(size, lifeTime, idleTime);

        DBServiceUserMS dbServiceUser = new DBCachedServiceUserImpl(new UserDaoImpl(sessionManagerHibernate), cache);

        SocketMessageTransferClient worker = new SocketMessageTransferClient(host, port, dbServiceAccount, dbServiceUser);

        executorService.submit(new UserSetGenerator((DBServiceUser)dbServiceUser));

        try {
            executorService.shutdown();
            logger.info("Cache created with size: {}, life time: {}, idle time: {}",cache.getSize(),lifeTime,idleTime);
            worker.start();
        }finally {
            worker.close();
        }
    }

    private void initialProperty() throws IOException {
        String propFileName = "application.properties";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
            Properties prop = new Properties();
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                logger.error("property file {} not found in the classpath", propFileName);
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            size = Integer.parseInt(prop.getProperty("cache.size"));
            lifeTime = Integer.parseInt(prop.getProperty("cache.lifeTime"));
            idleTime = Integer.parseInt(prop.getProperty("cache.idleTime"));
            host = prop.getProperty("socket.host");
            port = Integer.parseInt(prop.getProperty("socket.port"));

            logger.info("Properties: size={}, lifeTime={}, idleTime={}, host={}, port={}",size,lifeTime,idleTime,host,port);
        }
    }
}
