package com.it_uatech.server;

import com.it_uatech.api.model.AccountDataSet;
import com.it_uatech.api.service.DBServiceAccount;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginServlet extends HttpServlet {

    private DBServiceAccount serviceAccount;

    public LoginServlet(DBServiceAccount serviceAccount) {
        this.serviceAccount = serviceAccount;
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

    boolean validate(String userName, String password){
        Boolean result = false;
        if (!userName.isEmpty() && !password.isEmpty()){
            Optional<AccountDataSet> user = serviceAccount.findByLogin(userName);
            if (user.isPresent() && user.get().getPassword().equals(password)){
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
}
