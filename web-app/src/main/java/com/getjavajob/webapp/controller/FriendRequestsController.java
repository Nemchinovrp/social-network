package com.getjavajob.webapp.controller;


import com.getjavajob.models.Account;
import com.getjavajob.models.FriendRequest;
import com.getjavajob.service.AccountService;
import com.getjavajob.service.RequestService;
import com.getjavajob.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class FriendRequestsController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RequestService requestService;

    @RequestMapping("/account/requestFriend")
    public String addToFriend(@RequestParam("id") int accountReceiverId,
                              @SessionAttribute("acc") Account accountSender) throws ServiceException {
        Account receiver = accountService.getAccount(accountReceiverId);
        FriendRequest request = new FriendRequest(receiver, accountSender);
        requestService.createNewRequest(request);
        return "redirect:/account/acc?id=" + accountReceiverId;
    }

    @RequestMapping("/account/acceptFriendship")
    public String acceptFriendShip(@RequestParam("id") int senderId,
                                   @RequestParam("reqId") int reqId,
                                   @SessionAttribute("acc") Account account,
                                   HttpSession session) throws ServiceException {
        Account sender = accountService.getAccount(senderId);
        account.getFriends().add(sender);
        sender.getFriends().add(account);
        session.setAttribute("acc", account);

        requestService.remove(requestService.getRequest(reqId));
        return "redirect:/account/friends";
    }

    @RequestMapping("/account/declineFriendShip")
    public String declineFriendship(@RequestParam("reqId") int reqId) throws ServiceException{
        requestService.remove(requestService.getRequest(reqId));
        return "redirect:/account/friends";
    }

    @RequestMapping("/account/deleteFromFriend")
    public String deleteFromFriends(@RequestParam("id") int friendId,
                                    @SessionAttribute("acc") Account account,
                                    HttpSession session) throws ServiceException {
        Account friend = accountService.getAccount(friendId);
        friend.getFriends().remove(account);
        account.getFriends().remove(friend);
        accountService.updateAccount(friend);
        accountService.updateAccount(account);
        session.setAttribute("acc", account);
        return "redirect:/account/friends";
    }

}
