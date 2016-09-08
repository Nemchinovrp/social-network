package com.getjavajob.webapp.controller;

import com.getjavajob.models.Account;
import com.getjavajob.models.FriendRequest;
import com.getjavajob.models.Message;
import com.getjavajob.models.Phone;
import com.getjavajob.service.AccountService;
import com.getjavajob.service.MessageService;
import com.getjavajob.service.RequestService;
import com.getjavajob.service.ServiceException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private MessageService messageService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("name", "lastName", "password",
                "email", "workAddress", "homeAddress", "icq", "skype", "addInfo", "dateOfBirth");
    }

    @RequestMapping(value = "/")
    public String welcomePage() {
        return "login";
    }

    @RequestMapping(value = "/account/main")
    public String mainPage(@SessionAttribute("acc") Account owner, Model model) throws ServiceException {
        List<Message> wallMessages = messageService.getAllWallMsg(owner);
        model.addAttribute("wallMessages", wallMessages);
        return "profile";
    }

    @RequestMapping(value = "/account/friends")
    public ModelAndView showAllFriends(@SessionAttribute("acc") Account owner) throws ServiceException {
        ModelAndView modelAndView = new ModelAndView("friends");
        List<FriendRequest> requests = requestService.getAllRequestOfAcc(owner);
        modelAndView.addObject("requests", requests);
        return modelAndView;
    }

    @RequestMapping(value = "/account/update")
    public ModelAndView updatePage() {
        return new ModelAndView("update", "updAcc", new Account());
    }

    @RequestMapping(value = "/registration")
    public ModelAndView registrationPage() {
        return new ModelAndView("registration", "newUser", new Account());
    }

    @RequestMapping(value = "/login-check", method = RequestMethod.POST)
    public ModelAndView checkUser(@RequestParam("email") String email,
                                  @RequestParam("password") String password,
                                  @RequestParam(value = "isRemember", required = false) String isRemember,
                                  HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        Account acc;

        try {
            acc = accountService.getByEmailAndPass(email, password);
        } catch (ServiceException e) {
            return new ModelAndView("login", "error", "Unknown combination of email and password");
        }

        Cookie idCookie = new Cookie("id", String.valueOf(acc.getId()));

        if (isRemember != null) {
            idCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(idCookie);
        } else {
            idCookie.setMaxAge(0);
            response.addCookie(idCookie);
        }
        session.setAttribute("acc", acc);
        return new ModelAndView("redirect:/account/main");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logoutHandler(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = new Cookie("id", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        request.getSession().invalidate();
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/create_account", method = RequestMethod.POST)
    public ModelAndView createNewAccount(@ModelAttribute("newUser") Account account) {
        account.setRole(Account.Role.USER);
        try {
            accountService.createNewAccount(account);
        } catch (ServiceException e) {
            return new ModelAndView("registration", "error", "Account with this email already exist");
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/account/acc", method = RequestMethod.GET)
    public ModelAndView friendHandler(@RequestParam("id") int id,
                                      @SessionAttribute("acc") Account owner) throws ServiceException {

        ModelAndView modelAndView = new ModelAndView();
        Account account = accountService.getAccount(id);

        Boolean isFriend = false;
        Boolean isRequested = false;

        for (Account friend : owner.getFriends()) {
            if (friend.getId().equals(account.getId())) {
                isFriend = true;
            }
        }

        for (FriendRequest x: requestService.getAllRequestOfAcc(account)){
            if (x.getSender().equals(owner)){
                isRequested = true;
            }
        }

        List<Message> wallMessages = messageService.getAllWallMsg(account);

        modelAndView.setViewName("acc");
        modelAndView.addObject("acc", account);
        modelAndView.addObject("isFriend", isFriend);
        modelAndView.addObject("isRequested", isRequested);
        modelAndView.addObject("wallMessages", wallMessages);

        return modelAndView;
    }

    @RequestMapping(value = "/account/update/check", method = RequestMethod.POST)
    public String updateHandler(@SessionAttribute("acc") Account account,
                                HttpSession session, HttpServletRequest request,
                                @RequestParam(value = "img", required = false) MultipartFile file,
                                @RequestParam("phones[]") String[] phones) throws IOException, ServletException, ServiceException, ParseException {

        account.setName(request.getParameter("name"));
        account.setLastName(request.getParameter("lastName"));
        account.setAddInfo(request.getParameter("addInfo"));
        account.setSkype(request.getParameter("skype"));
        account.setIcq(request.getParameter("icq"));
        account.setHomeAddress(request.getParameter("homeAddress"));
        account.setWorkAddress(request.getParameter("workAddress"));
        account.setEmail(request.getParameter("email"));
        account.setPassword(request.getParameter("password"));
        Date dateOfBirthParsed = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateOfBirth"));
        account.setDateOfBirth(dateOfBirthParsed);

        List<Phone> phonesList = new ArrayList<>();
        for (String p : phones) {
            Phone phone = new Phone();
            phone.setPhone(p);
            phone.setOwner(account);
            phonesList.add(phone);
        }

        account.setPhones(phonesList);

        if (!file.isEmpty()) {
            account.setImage(file.getBytes());
        }

        accountService.updateAccount(account);
        session.setAttribute("acc", account);

        return "redirect:/account/main";
    }

    @RequestMapping(value = "/searchByAccounts", method = RequestMethod.GET)
    @ResponseBody
    public List<Account> getAccountsByFilter(final @RequestParam("filter") String filter,
                                             final @SessionAttribute("acc") Account accSession) throws ServiceException {
        List<Account> accounts = accountService.getAllAccounts();
        CollectionUtils.filter(accounts, new Predicate<Account>() {

            @Override
            public boolean evaluate(Account account) {
                return (account.getName().contains(filter) ||
                        account.getLastName().contains(filter)) && !account.getId().equals(accSession.getId());
            }
        });
        return accounts;
    }
}
