package com.it_uatech.server;

import com.it_uatech.api.cache.CacheEngine;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.service.MessageSystemContext;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class StatisticWebSocketServlet extends WebSocketServlet {

    private final static long LOGOUT_TIME = TimeUnit.MINUTES.toMillis(10);
    private static final Logger logger = LoggerFactory.getLogger(StatisticWebSocketServlet.class);

    private ApplicationContext appContext;
    private CacheEngine<Long, UserDataSet> cache;
    private MessageSystemContext msgSystemCtx;

    public StatisticWebSocketServlet() {
        logger.info("Websocket servlet created");
    }

    private void initVariables() {
        logger.info("Call websocket init method");
        if (Objects.isNull(appContext)) {
            appContext = (ApplicationContext) getServletContext().getAttribute("appContext");
        }
        logger.info("Application context handled: {}", appContext.getStartupDate());
        this.cache = (CacheEngine<Long, UserDataSet>) appContext.getBean("cache");
        this.msgSystemCtx = (MessageSystemContext) appContext.getBean("messageSystemContext");
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        logger.info("Call websocket configure method");
        initVariables();
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new StatisticWebSocketCreator(msgSystemCtx));
    }

    @Override
     protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("call get method");
        Object userAttr = req.getSession().getAttribute("user");
        if ((userAttr != null) && userAttr.equals("authorised")) {
            resp.getWriter().println(getPageStatistic());
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("call put method");
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("cacheHit", cache.getHitCount());
        pageVariables.put("cacheMiss", cache.getMissCount());
        resp.getWriter().println(TemplateProcessor.instance().getPage("cache.json", pageVariables));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private String getPageStatistic() throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("cacheSize", cache.getSize());
        return TemplateProcessor.instance().getPage("statistic.html", pageVariables);
    }

    ApplicationContext getAppContext() {
        return appContext;
    }
}
