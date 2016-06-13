package com.tsystems.logiweb.dao.impl;



import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by mvolkov on 13.06.2016.
 */
public class BaseDao {
    protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("logiweb");
    protected EntityManager em = emf.createEntityManager();
    protected EntityTransaction tr = em.getTransaction();

    public void save(Object object) {
        try{
            tr.begin();
            em.persist(object);
            tr.commit();
        }
        finally
        {
            if (tr.isActive())
            {
                tr.rollback();
            }
        }
    }

    public void merge(Object object){
        tr.begin();
        em.merge(object);
        tr.commit();
    }
}
