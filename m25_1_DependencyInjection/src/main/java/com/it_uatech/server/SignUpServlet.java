package com.it_uatech.server;

import com.it_uatech.api.model.AccountDataSet;
import com.it_uatech.api.service.DBServiceAccount;
import com.it_uatech.api.service.DataServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends ParentServlet {

    private  static final Logger logger = LoggerFactory.getLogger(SignUpServlet.class);

    private DBServiceAccount serviceAccount;

    public SignUpServlet() {
    }

    public SignUpServlet(DBServiceAccount serviceAccount) {
        this.serviceAccount = serviceAccount;
    }

    @Override
    public void init(){
        super.init();
        logger.info("Call signUpServlet init method");
        this.serviceAccount = (DBServiceAccount) getAppContext().getBean("dbServiceAccount");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userLogin = req.getParameter("login");
        String password = req.getParameter("password");
        if (!userLogin.isEmpty() && !password.isEmpty()){
            try{
                saveUser(userLogin,password);
                resp.getWriter().println(getPage());
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
            }catch (DataServiceException ex){
                ex.printStackTrace();
            }
        }
    }

    void saveUser(String userName, String password){
        AccountDataSet ads = new AccountDataSet(userName,password);
        serviceAccount.save(ads);
    }

    private String getPage() throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("signUpStatus","Success");
        return TemplateProcessor.instance().getPage("signup.html", pageVariables);
    }
}
