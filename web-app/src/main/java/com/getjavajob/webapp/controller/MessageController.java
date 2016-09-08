package com.getjavajob.webapp.controller;

import com.getjavajob.models.Account;
import com.getjavajob.models.Message;
import com.getjavajob.service.AccountService;
import com.getjavajob.service.MessageService;
import com.getjavajob.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;

    @RequestMapping("/account/messages")
    public String conversationPage(@RequestParam("friendId") int friendId,
                                   Model model,
                                   @SessionAttribute("acc") Account receiver) throws ServiceException {
        Account friend = accountService.getAccount(friendId);
        List<Message> messageList = messageService.getAllMsgFromTwoAcc(friend, receiver);

        model.addAttribute("friend", friend);
        model.addAttribute("messageList", messageList);

        for (Message x: messageList){
            x.setRead(true);
            messageService.updateReadStatus(x);
        }
        return "converse";
    }

    @RequestMapping("/addWallMsg")
    public String addWallMsg(@RequestParam("wallPost") String msg,
                             @SessionAttribute("acc") Account sender,
                             @RequestParam("receiverId") int receiverId) throws ServiceException {
        Account receiver = accountService.getAccount(receiverId);
        Message message = new Message(sender, receiver, msg, Message.Type.ACC_WALL, true);
        messageService.createMsg(message);
        return "redirect:/account/acc?id=" + receiverId;
    }

    @RequestMapping("/account/deleteWallMsg")
    public String removeWallMsg(@RequestParam("msgId") int msgId) throws ServiceException {
        Message message = messageService.getMessageById(msgId);
        messageService.removeWallMsg(message);
        return "redirect:/account/main";
    }

    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public ModelAndView sendMsg (@RequestParam("senderId") int senderId,
                                 @RequestParam("receiverId") int receiverId,
                                 @RequestParam("text") String text) throws ServiceException {
        Account sender = accountService.getAccount(senderId);
        Account receiver = accountService.getAccount(receiverId);
        Message message = new Message(sender, receiver, text, Message.Type.ACC_PRIVATE, false);
        messageService.createMsg(message);
        List<Message> messageList = messageService.getAllMsgFromTwoAcc(sender, receiver);
        ModelAndView modelAndView = new ModelAndView("converse");
        modelAndView.addObject("friend", receiver);
        modelAndView.addObject("messageList", messageList);
        return modelAndView;
    }
}
