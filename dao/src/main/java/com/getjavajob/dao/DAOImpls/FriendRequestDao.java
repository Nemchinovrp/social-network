package com.getjavajob.dao.DAOImpls;

import com.getjavajob.dao.CommonDao;
import com.getjavajob.dao.DAOException;
import com.getjavajob.models.Account;
import com.getjavajob.models.FriendRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class FriendRequestDao extends CommonDao<FriendRequest> {
    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    public FriendRequestDao() {
        super(FriendRequest.class);
    }

    public void delete(FriendRequest request) throws DAOException {
        em.remove(em.contains(request) ? request : em.merge(request));
    }

    public List<FriendRequest> getAllRequest(Account owner) throws DAOException{
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<FriendRequest> query = cb.createQuery(FriendRequest.class);
        Root<FriendRequest> root = query.from(FriendRequest.class);
        query.select(root).where(
            cb.equal(root.get("receiver"), owner.getId())
        );
        return em.createQuery(query).getResultList();
    }

    @Override
    public void update(FriendRequest request) throws DAOException {
        throw new UnsupportedOperationException();
    }
}
