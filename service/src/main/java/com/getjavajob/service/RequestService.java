package com.getjavajob.service;

import com.getjavajob.dao.DAOException;
import com.getjavajob.dao.DAOImpls.AccountDao;
import com.getjavajob.dao.DAOImpls.FriendRequestDao;
import com.getjavajob.models.Account;
import com.getjavajob.models.FriendRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RequestService {
    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    @Autowired
    private FriendRequestDao requestDao;

    public FriendRequest createNewRequest(FriendRequest request) throws ServiceException {
        FriendRequest req;
        try {
            req = requestDao.create(request);
        } catch (DAOException e) {
            throw new ServiceException("Exception during creating new account", e);
        }
        return req;
    }

    public FriendRequest getRequest(int id) throws ServiceException {
        FriendRequest request;
        try {
            request = requestDao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during retrieving account", e);
        }
        return request;
    }

    public void remove(FriendRequest request) throws ServiceException {
        try {
            requestDao.delete(request);
        } catch (DAOException e) {
            throw new ServiceException("Exception during retrieving account", e);
        }
    }

    public List<FriendRequest> getAllRequestOfAcc(Account account) throws ServiceException {
        List<FriendRequest> requests = new ArrayList<>();
        try {
            requests = requestDao.getAllRequest(account);
        } catch (DAOException e) {
            throw new ServiceException("Exception during retrieving account", e);
        }
        return requests;
    }
}