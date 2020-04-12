package com.it_uatech.application;

import com.it_uatech.api.service.MessageSystemContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    private  static final Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Servlet context initialized");
        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("appContext",appContext);

        UserSetGenerator generator = (UserSetGenerator) appContext.getBean("generator");
        new Thread(generator).start();

        MessageSystemContext messageSystemContext = (MessageSystemContext) appContext.getBean("messageSystemContext");
        messageSystemContext.getMessageSystem().start();
    }
}
