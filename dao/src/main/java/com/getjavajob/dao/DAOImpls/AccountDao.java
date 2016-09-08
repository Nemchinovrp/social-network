package com.getjavajob.dao.DAOImpls;

import com.getjavajob.dao.DAOException;
import com.getjavajob.dao.CommonDao;
import com.getjavajob.models.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository
public class AccountDao extends CommonDao<Account> {
    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    public AccountDao() {
        super(Account.class);
    }

    @Override
    public Account getById(int id) throws DAOException {
        Account account = em.find(super.clazz, id);
        if (account != null){
            account.getPhones().size();
            account.getFriends().size();
        }
        return account;
    }

    public List<Account> getAll() throws DAOException{
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> rootEntry = cq.from(Account.class);
        CriteriaQuery<Account> all = cq.select(rootEntry);
        return em.createQuery(all).getResultList();
    }

    public Account getByEmailAndPass(String email, String password) throws DAOException {
        Account account;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        query.select(root).where(
                cb.and(
                        cb.equal(root.get("email"), email),
                        cb.equal(root.get("password"), password)));
        TypedQuery<Account> typedQuery = em.createQuery(query);
        account = typedQuery.getSingleResult();
        account.getFriends().size(); //init
        account.getPhones().size();
        return account;
    }

}
