package com.getjavajob.dao.DAOImpls;

import com.getjavajob.dao.CommonDao;
import com.getjavajob.dao.DAOException;
import com.getjavajob.models.Account;
import com.getjavajob.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class MessageDao extends CommonDao<Message> {
    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    public MessageDao() {
        super(Message.class);
    }

    public List<Message> getAllFromAcc(Account sender, Account receiver) throws DAOException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        query.select(root).where(
                cb.or(
                        cb.and(
                                cb.equal(root.get("sender"), sender.getId()),
                                cb.equal(root.get("receiver"), receiver.getId()),
                                cb.equal(root.get("type"), Message.Type.ACC_PRIVATE)
                        ),
                        cb.and(
                                cb.equal(root.get("sender"), receiver.getId()),
                                cb.equal(root.get("receiver"), sender.getId()),
                                cb.equal(root.get("type"), Message.Type.ACC_PRIVATE)
                        )));
        return em.createQuery(query).getResultList();
    }

    public List<Message> getAllWallMsg(Account wallOwner) throws DAOException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        query.select(root).where(
                cb.and(
                        cb.equal(root.get("receiver"), wallOwner.getId()),
                        cb.equal(root.get("type"), Message.Type.ACC_WALL)));
        return em.createQuery(query).getResultList();
    }

    public void deleteWallMsg(Message msg) throws DAOException {
        em.remove(em.contains(msg) ? msg : em.merge(msg));
    }

    public List<Message> getAllUnreadMsgs(Account owner) throws DAOException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        query.select(root).where(
                cb.and(
                        cb.equal(root.get("receiver"), owner.getId()),
                        cb.equal(root.get("type"), Message.Type.ACC_PRIVATE)),
                cb.equal(root.get("isRead"), false));
        return em.createQuery(query).getResultList();
    }

    public List<Message> getAllUnreadBeetwenTwoAcc(Account owner, Account friend) throws DAOException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        query.select(root).where(
                cb.and(
                        cb.equal(root.get("receiver"), owner.getId()),
                        cb.equal(root.get("sender"), friend.getId()),
                        cb.equal(root.get("type"), Message.Type.ACC_PRIVATE)),
                cb.equal(root.get("isRead"), false)
        );
        return em.createQuery(query).getResultList();
    }

}
