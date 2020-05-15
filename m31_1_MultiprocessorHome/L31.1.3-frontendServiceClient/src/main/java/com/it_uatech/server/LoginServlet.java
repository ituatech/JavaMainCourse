package com.it_uatech.server;

import com.it_uatech.api.FrontendResponseMessage;
import com.it_uatech.api.FrontendServiceAccount;
import com.it_uatech.app.FrontendServiceAccountMS;
import com.it_uatech.dto.AccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class LoginServlet extends ParentServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    private FrontendServiceAccount serviceAccount;

    public LoginServlet() {
    }

    public LoginServlet(FrontendServiceAccount serviceAccount) {
        this.serviceAccount = serviceAccount;
    }

    @Override
    public void init(){
        super.init();
        logger.info("Call loginServlet init method");
        this.serviceAccount = (FrontendServiceAccount) getAppContext().getBean("serviceAccount");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userLogin = req.getParameter("login");
        String password = req.getParameter("password");

        if (validate(userLogin,password)){
            req.getSession().setAttribute("user","authorised");
            resp.sendRedirect("/statistic");
        }else{
            resp.getWriter().println(getPage(userLogin));
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    boolean validate(String userNameReq, String passwordReq){
        int whileStatementCount = 0;
        boolean result = false;
        if (!userNameReq.isEmpty() && !passwordReq.isEmpty()){
            int randomGeneratedKey = ThreadLocalRandom.current().nextInt(1000_000, 1000_000_000);
            serviceAccount.handleRequestGetAccountByLogin(userNameReq,randomGeneratedKey);
            while (!serviceAccount.getHttpRequestMap().containsKey(randomGeneratedKey) && serviceAccount.isConnected() && whileStatementCount < 1000)
            {
                logger.info("Wait account data from database");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                whileStatementCount++;
            }
            AccountDTO account = serviceAccount.getHttpRequestMap().remove(randomGeneratedKey);

            if (account.getLogin().equals(userNameReq) && account.getPassword().equals(passwordReq)){
                result = true;
            }
        }
        return result;
    }

    private String getPage(String login) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("logName",login);
        return TemplateProcessor.instance().getPage("loginFailed.html", pageVariables);
    }

    @Override
    public void destroy() {
        super.destroy();
        serviceAccount.shutdown();
    }
}




