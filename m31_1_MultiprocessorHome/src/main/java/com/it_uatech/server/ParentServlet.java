package com.it_uatech.server;

import com.it_uatech.api.service.MessageSystemContext;
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

    @Override
    public void destroy() {
        super.destroy();
        MessageSystemContext msgSystemContext = (MessageSystemContext) appContext.getBean("messageSystemContext");
        msgSystemContext.getMessageSystem().dispose();
        logger.info("Call parent destroy method");
    }

    ApplicationContext getAppContext() {
        return appContext;
    }
}
