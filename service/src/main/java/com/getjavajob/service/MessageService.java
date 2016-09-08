package com.getjavajob.service;

import com.getjavajob.dao.DAOException;
import com.getjavajob.dao.DAOImpls.MessageDao;
import com.getjavajob.models.Account;
import com.getjavajob.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public Message createMsg(Message msg) throws ServiceException {
        Message message;
        try {
            message = messageDao.create(msg);
        } catch (DAOException e) {
            throw new ServiceException("Cant create a msg", e);
        }
        return message;
    }

    public Message getMessageById(int id)throws ServiceException{
        Message message;
        try {
            message = messageDao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException("Cant retrieve msg", e);
        }
        return message;
    }

    public List<Message> getAllMsgFromTwoAcc(Account sender, Account receiver) throws ServiceException {
        List<Message> messageList;
        try {
            messageList = messageDao.getAllFromAcc(sender, receiver);
        } catch (DAOException e) {
            throw new ServiceException("Cant generate set of messages", e);
        }
        return messageList;
    }

    public List<Message> getAllWallMsg(Account receiver) throws ServiceException{
        List<Message> messageList;
        try {
            messageList = messageDao.getAllWallMsg(receiver);
        } catch (DAOException e) {
            throw new ServiceException("cant prepare list of wall messages", e);
        }
        return messageList;
    }

    public void removeWallMsg(Message message)throws ServiceException{
        try {
            messageDao.deleteWallMsg(message);
        } catch (DAOException e) {
            throw new ServiceException("Cant delete wall msg", e);
        }
    }

    public List<Message> getAllUnreadMsgs(Account owner) throws ServiceException{
        List<Message> messageList;
        try {
            messageList = messageDao.getAllUnreadMsgs(owner);
        } catch (DAOException e) {
            throw new ServiceException("Cant retrieve all msgs", e);
        }
        return messageList;
    }

    public void updateReadStatus(Message message)throws ServiceException{
        try {
            messageDao.update(message);
        } catch (DAOException e) {
            throw new ServiceException("Cant update msg", e);
        }
    }
}
