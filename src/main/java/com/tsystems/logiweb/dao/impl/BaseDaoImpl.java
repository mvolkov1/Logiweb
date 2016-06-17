package com.tsystems.logiweb.dao.impl;


import com.tsystems.logiweb.dao.api.BaseDao;

import javax.persistence.*;
import java.util.List;

/**
 * Created by mvolkov on 13.06.2016.
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

    protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("logiweb");
    protected EntityManager em = emf.createEntityManager();
    protected EntityTransaction tr = em.getTransaction();

    public void save(T object) {
        try {
            tr.begin();
            em.persist(object);
            tr.commit();
        } finally {
            if (tr.isActive()) {
                tr.rollback();
            }
        }
    }


    public List<T> getAllEntities(Class c) {
        String stringQuery = "select object(o) from " + c.getSimpleName() + " o";
        Query query = em.createQuery("select object(o) from " + c.getSimpleName() + " o");
        return (List<T>) query.getResultList();
    }

    public int getNumberOfEntities(Class c) {
        Query query = em.createQuery("select count (*) from " + c.getSimpleName());
        return (Integer) query.getSingleResult();
    }
}
