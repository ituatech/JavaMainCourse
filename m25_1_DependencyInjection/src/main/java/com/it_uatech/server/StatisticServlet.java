package com.it_uatech.server;

import com.it_uatech.api.cache.CacheEngine;
import com.it_uatech.api.model.UserDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StatisticServlet extends ParentServlet {

    private  static final Logger logger = LoggerFactory.getLogger(StatisticServlet.class);

    CacheEngine<Long, UserDataSet> cache;

    public StatisticServlet() {
    }

    public StatisticServlet(CacheEngine<Long, UserDataSet> cache) {
        this.cache = cache;
    }

    @Override
    public void init(){
        super.init();
        logger.info("Call statisticServlet init method");
        this.cache = (CacheEngine<Long, UserDataSet>) getAppContext().getBean("cache");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Object userAttr = req.getSession().getAttribute("user");
        if ((userAttr != null) && userAttr.equals("authorised")) {
            resp.getWriter().println(getPage());
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String getPage() throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("cacheSize", cache.getSize());
        pageVariables.put("cacheHit", cache.getHitCount());
        pageVariables.put("cacheMiss", cache.getMissCount());
        return TemplateProcessor.instance().getPage("statistic.html", pageVariables);
    }
}
