package com.it_uatech.server;

import com.it_uatech.api.model.AccountDataSet;
import com.it_uatech.api.service.DBServiceAccount;
import com.it_uatech.api.service.DataServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet {

    private DBServiceAccount serviceAccount;

    public SignUpServlet(DBServiceAccount serviceAccount) {
        this.serviceAccount = serviceAccount;
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
