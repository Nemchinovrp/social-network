package com.getjavajob.webapp.controller;

import com.getjavajob.models.Account;
import com.getjavajob.service.AccountService;
import com.getjavajob.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class AvatarController {

    @Autowired
    private AccountService accountService;

    @ResponseBody
    @RequestMapping(value = "/account/display_avatar", method = RequestMethod.GET)
    public byte[] accountAvatarHandler(@SessionAttribute("acc") Account account) throws IOException {
        return account.getImage();
    }

    @ResponseBody
    @RequestMapping(value = "/account/friend/display_avatar", method = RequestMethod.GET)
    public byte[] friendAvatarHandler(@RequestParam("id") int id) throws IOException, ServiceException {
        Account owner = accountService.getAccount(id);
        if (owner != null) {
            return owner.getImage();
        }
        return null;
    }
}
