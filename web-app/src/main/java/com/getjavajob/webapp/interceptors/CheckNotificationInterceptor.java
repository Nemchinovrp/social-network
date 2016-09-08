package com.getjavajob.webapp.interceptors;

import com.getjavajob.models.Account;
import com.getjavajob.models.FriendRequest;
import com.getjavajob.models.Message;
import com.getjavajob.service.MessageService;
import com.getjavajob.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

public class CheckNotificationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RequestService requestService;

    @Autowired
    private MessageService messageService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("acc") != null) {
            Account owner = (Account) request.getSession().getAttribute("acc");

            List<Message> messageList = messageService.getAllUnreadMsgs(owner);
            if (messageList != null) {
                request.setAttribute("msgCount", messageList.size());

                Set<Account> senders = new HashSet<>();
                for (Message x: messageList){
                    senders.add(x.getSender());
                }
                request.setAttribute("senders", senders);

                request.setAttribute("msgList", messageList);
            } else {
                request.setAttribute("msgCount", 0);
            }

            List<FriendRequest> requests = requestService.getAllRequestOfAcc(owner);
            if (requests != null) {
                request.setAttribute("count", requests.size());
            } else {
                request.setAttribute("count", 0);
            }
            return true;
        }else {
            return true;
        }
    }
}
