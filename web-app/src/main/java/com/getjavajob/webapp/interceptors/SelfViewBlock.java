package com.getjavajob.webapp.interceptors;

import com.getjavajob.models.Account;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SelfViewBlock extends HandlerInterceptorAdapter {

    private static final String SELF_PATH = "account/acc";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        Account account;
        if (session != null && session.getAttribute("acc") != null) {
            account = (Account) session.getAttribute("acc");

            if (request.getRequestURI().contains(SELF_PATH) && request.getParameter("id").equals(account.getId().toString())){
                response.sendRedirect("/account/main");
                return false;
            }else {
                return true;
            }
        }
        return true;
    }
}
