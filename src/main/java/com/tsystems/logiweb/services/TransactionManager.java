package com.tsystems.logiweb.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by mvolkov on 17.06.2016.
 */
public class TransactionManager {

    private static final EntityManagerFactory emf;
    private static final ThreadLocal<EntityManager> tlEntityManager;

    static{
        emf = Persistence.createEntityManagerFactory("logiweb");
        tlEntityManager =  new ThreadLocal<EntityManager>();
    }

    public static EntityManager getEntityManager() {

        EntityManager entityManager = tlEntityManager.get();
        if (entityManager==null){
            entityManager = emf.createEntityManager();
            tlEntityManager.set(entityManager);
        }
        return entityManager;
    }

    public static void closeEntityManager() {
        EntityManager entityManager = tlEntityManager.get();
        if (entityManager != null) {
            entityManager.close();
            tlEntityManager.set(null);
        }
    }

    public static void closeEntityManagerFactory() {
        emf.close();
    }

    public static void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    public static void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    public static void commit() {
        getEntityManager().getTransaction().commit();
    }


//    public void commitTransaction() {
//        entityManager.getTransaction().commit();
//    }
//
//    public void rollbackTransaction() {
//        if (entityManager.getTransaction().isActive()) {
//            entityManager.getTransaction().rollback();
//        }
//    }

}
