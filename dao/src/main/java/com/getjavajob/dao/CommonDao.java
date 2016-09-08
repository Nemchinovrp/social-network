package com.getjavajob.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class CommonDao<T>{

    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> clazz;

    public CommonDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T create(T t) throws DAOException {
        em.persist(t);
        return t;
    }

    public T getById(int id) throws DAOException {
        return em.find(clazz, id);
    }

    public void update(T t) throws DAOException {
        em.merge(t);
    }
}
