package com.getjavajob.dao.DAOImpls;

import com.getjavajob.dao.DAOException;
import com.getjavajob.dao.CommonDao;
import com.getjavajob.models.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PhoneDao extends CommonDao<Phone> {
    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);
    public PhoneDao() {
        super(Phone.class);
    }

    public void delete(Phone phone) throws DAOException{
        em.remove(em.contains(phone) ? phone : em.merge(phone));
    }
}
