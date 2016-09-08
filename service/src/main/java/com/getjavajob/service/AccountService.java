package com.getjavajob.service;

import com.getjavajob.dao.DAOException;
import com.getjavajob.dao.DAOImpls.AccountDao;
import com.getjavajob.models.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    @Autowired
    private AccountDao accountDao;

    public Account getByEmailAndPass(String email, String password) throws ServiceException {
        Account account;
        try {
            account = accountDao.getByEmailAndPass(email, password);
        } catch (DAOException e) {
            throw new ServiceException("exception when getting an account", e);
        }
        return account;
    }

    public void updateAccount(Account account) throws ServiceException {
        try {
            accountDao.update(account);
        } catch (DAOException e) {
            throw new ServiceException("updating error", e);
        }
    }

    public Account createNewAccount(Account account) throws ServiceException {
        Account acc;
        try {
            acc = accountDao.create(account);
        } catch (DAOException e) {
            throw new ServiceException("Exception during creating new account", e);
        }
        return acc;
    }

    public Account getAccount(int id) throws ServiceException {
        Account account;
        try {
            account = accountDao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during retrieving account", e);
        }
        return account;
    }

    public List<Account> getAllAccounts() throws ServiceException {
        List<Account> accounts;
        try {
            accounts = accountDao.getAll();
        } catch (DAOException e) {
            throw new ServiceException("Exception during retrieving account", e);
        }
        return accounts;
    }
}
