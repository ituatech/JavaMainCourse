package com.it_uatech.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServlet;
import java.util.Objects;

public class ParentServlet extends HttpServlet {

    private  static final Logger logger = LoggerFactory.getLogger(ParentServlet.class);

    private ApplicationContext appContext;

    @Override
    public void init() {
        logger.info("Call parent init method");
        if (Objects.isNull(appContext)){
            appContext = (ApplicationContext) getServletContext().getAttribute("appContext");
        }
        logger.info("Application context handled: {}", appContext.getStartupDate());
    }

    ApplicationContext getAppContext() {
        return appContext;
    }
}
