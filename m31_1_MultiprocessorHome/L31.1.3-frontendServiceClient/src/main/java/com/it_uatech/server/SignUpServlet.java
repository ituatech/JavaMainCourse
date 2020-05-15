package com.it_uatech.server;

import com.it_uatech.api.FrontendServiceAccount;
import com.it_uatech.dto.AccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends ParentServlet {

    private static final Logger logger = LoggerFactory.getLogger(SignUpServlet.class);

    private FrontendServiceAccount serviceAccount;

    public SignUpServlet() {
    }

    public SignUpServlet(FrontendServiceAccount serviceAccount) {
        this.serviceAccount = serviceAccount;
    }

    @Override
    public void init() {
        super.init();
        logger.info("Call signUpServlet init method");
        this.serviceAccount = (FrontendServiceAccount) getAppContext().getBean("serviceAccount");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userLogin = req.getParameter("login");
        String password = req.getParameter("password");
        if (!userLogin.isEmpty() && !password.isEmpty()) {
            saveAccount(userLogin, password);
            resp.getWriter().println(getPage());
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    void saveAccount(String login, String password) {
        serviceAccount.handleRequestRegisterNewAccount(new AccountDTO(login, password));
    }

    private String getPage() throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("signUpStatus", "Success");
        return TemplateProcessor.instance().getPage("signup.html", pageVariables);
    }

    @Override
    public void destroy() {
        super.destroy();
        serviceAccount.shutdown();
    }
}
