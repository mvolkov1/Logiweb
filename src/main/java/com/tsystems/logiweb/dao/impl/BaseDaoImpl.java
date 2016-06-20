package com.tsystems.logiweb.dao.impl;


import com.tsystems.logiweb.dao.TransactionManager;
import com.tsystems.logiweb.dao.api.BaseDao;

import javax.persistence.*;
import java.util.List;

/**
 * Created by mvolkov on 13.06.2016.
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {


    protected EntityManager entityManager;

    public BaseDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }



    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(T object) {
        entityManager.persist(object);
    }

    public void refresh(T object) {entityManager.refresh(object);}



    public List<T> getAllEntities(Class c) {
        String stringQuery = "select object(o) from " + c.getSimpleName() + " o";
        Query query = entityManager.createQuery("select object(o) from " + c.getSimpleName() + " o");
        List<T> ret = null;
        try
        {
            ret = (List<T>) query.getResultList();
        }
        catch (Exception e)
        {
            ret = null;
        }
        return ret;
    }

    public int getNumberOfEntities(Class c) {
        Query query = entityManager.createQuery("select count (*) from " + c.getSimpleName());
        return (Integer) query.getSingleResult();
    }
}
