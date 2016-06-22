package com.tsystems.logiweb.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by mvolkov on 17.06.2016.
 */
public class TransactionManager implements AutoCloseable {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("logiweb");
    private EntityManager entityManager = emf.createEntityManager();

//    ThreadLocal<List<Whatever>> myThreadLocal =
//            ThreadLocal.withInitial(ArrayList::new);

    public EntityManager getEntityManager() {
        return entityManager;
    }

    static TransactionManager tm;

    private TransactionManager() {
        super();
    }

    static public TransactionManager getInstance() {
        if (tm == null) {
            tm = new TransactionManager();
        }
        return tm;
    }

    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    public void commitTransaction() {
        entityManager.getTransaction().commit();
    }

    public void rollbackTransaction() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    public void close() throws Exception {
        entityManager.close();
        emf.close();
    }
}
