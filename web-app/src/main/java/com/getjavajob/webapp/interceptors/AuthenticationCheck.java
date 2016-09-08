package com.getjavajob.webapp.interceptors;

import com.getjavajob.models.Account;
import com.getjavajob.service.AccountService;
import com.getjavajob.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationCheck extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationCheck.class);

    private static final String LOGIN_PATH = "/";
    private static final String REGISTRATION_PATH = "/registration";
    private static final String ACCOUNT_PATH = "account";

    @Autowired
    private AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("preHandle(...) ->");
        HttpSession session = request.getSession(false);
        logger.debug("session present: " + (session != null));
        logger.debug("requested URI: " + request.getRequestURI());

        if (request.getRequestURI().equals(LOGIN_PATH) || request.getRequestURI().equals(REGISTRATION_PATH)) {
            if (session != null && session.getAttribute("acc") != null) {
                response.sendRedirect("/account/main");
                logger.debug("redirecting to /account/main");
                return true;
            }else if (getCookieValue(request) > 0){
                Account account = null;
                try {
                    logger.debug("retrieving account from db");
                    account = accountService.getAccount(getCookieValue(request));
                } catch (ServiceException e) {
                    logger.warn("cant fetch account", e);
                }
                if (account != null) {
                    request.getSession().setAttribute("acc", account);
                    response.sendRedirect("/account/main");
                    logger.debug("redirecting to /account/main");
                    return true;
                }
            }
        }

        if (request.getRequestURI().contains(ACCOUNT_PATH)){
            if (session != null && session.getAttribute("acc") != null) {
                return true;
            }else if (getCookieValue(request) > 0){
                Account account = null;
                try {
                    logger.debug("retrieving account from db");
                    account = accountService.getAccount(getCookieValue(request));
                } catch (ServiceException e) {
                    logger.warn("cant fetch account", e);
                }
                if (account != null) {
                    request.getSession().setAttribute("acc", account);
                    return true;
                }
            }
        }
        return true;
    }

    private int getCookieValue(HttpServletRequest request) {
        logger.debug("trying to retrieve cookies");
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    return Integer.parseInt(cookie.getValue());
                }
            }
        }
        logger.debug("cookie not found -> return 0");
        return 0;
    }
}
