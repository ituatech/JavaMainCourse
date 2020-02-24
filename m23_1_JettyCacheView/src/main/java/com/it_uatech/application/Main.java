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
import com.it_uatech.api.service.DBServiceAccountImpl;
import com.it_uatech.api.service.DBServiceUser;
import com.it_uatech.hibernate_impl.HibernateUtil;
import com.it_uatech.hibernate_impl.dao.AccountDaoImpl;
import com.it_uatech.hibernate_impl.dao.UserDaoImpl;
import com.it_uatech.hibernate_impl.sessionmanager.SessionManagerHibernate;
import com.it_uatech.server.LoginServlet;
import com.it_uatech.server.SignUpServlet;
import com.it_uatech.server.StatisticServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.SessionFactory;

import java.util.Random;

public class Main {

    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "src/main/resources/public_html";

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory("hibernate.cfg", AccountDataSet.class,UserDataSet.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        AccountDao dao = new AccountDaoImpl(sessionManager);
        DBServiceAccountImpl serviceAccount = new DBServiceAccountImpl(dao);
        CacheEngine<Long, UserDataSet> cache = new CacheEngineImpl<>(16,10000,0);
        UserDao userDao = new UserDaoImpl(sessionManager);
        DBServiceUser userService = new DBCachedServiceUserImpl(userDao,cache);


        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new LoginServlet(serviceAccount)),"/login");
        context.addServlet(new ServletHolder(new SignUpServlet(serviceAccount)),"/signup");
        context.addServlet(new ServletHolder(new StatisticServlet(cache)),"/statistic");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();

        int i = 0;
        while (i < 300){
            UserDataSet uds1 = new UserDataSet("kolyan"+Math.random()*10, (int)(Math.random()*100));
            UserDataSet uds2 = new UserDataSet("kolyan"+Math.random()*10, (int)(Math.random()*100));

            long id = userService.save(uds1);
            userService.save(uds2);

            Thread.sleep(1000);

            userService.findById((long) (Math.random()*id));
        }




        server.join();
    }
}
