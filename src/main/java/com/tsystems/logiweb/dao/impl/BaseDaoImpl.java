package com.tsystems.logiweb.dao.impl;


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

    public BaseDaoImpl() {
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void flush() {
        entityManager.flush();
    }

    public void save(T object) {
        try {
            entityManager.persist(object);
        } catch (EntityExistsException e) //- if the entity already exists. (If the entity already exists, the EntityExistsException may be thrown when the persist operation is invoked, or the EntityExistsException or another PersistenceException may be thrown at flush or commit time.)
        {
            throw e;
        } catch (IllegalArgumentException e) // - if the instance is not an entity
        {
            throw e;
        } catch (TransactionRequiredException e) //- if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
        {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public void merge(T t) {
        try {
            entityManager.merge(t);
        } catch (Exception e) {
            // TODO logging
            throw e;
        }
    }

    public void refresh(T object) {
        entityManager.refresh(object);
    }

    public void remove(T t) {
        try {
            entityManager.remove(t);
        } catch (IllegalArgumentException e) //- if the instance is not an entity or is a detached entity
        {
            throw e;
        } catch (TransactionRequiredException e) //- if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
        {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }


    public List<T> getAllEntities(Class c) {

        String stringQuery = "select object(o) from " + c.getSimpleName() + " o";
        Query query = entityManager.createQuery(stringQuery);

        List<T> ret = null;
        try {
            ret = (List<T>) query.getResultList();
        } catch (IllegalStateException e) //- if called for a Java Persistence query language UPDATE or DELETE statement
        {
            throw e;
        } catch (QueryTimeoutException e) //if the query execution exceeds the query timeout value set and only the statement is rolled back
        {
            throw e;
        } catch (TransactionRequiredException e) //if a lock mode has been set and there is no transaction
        {
            throw e;
        } catch (PessimisticLockException e)//- if pessimistic locking fails and the transaction is rolled back
        {
            throw e;
        } catch (LockTimeoutException e)// - if pessimistic locking fails and only the statement is rolled back
        {
            throw e;
        } catch (PersistenceException e)//
        {
            throw e;
        } catch (Exception e) {
            ret = null;
        }
        return ret;
    }

    public int getNumberOfEntities(Class c) {
        Query query = entityManager.createQuery("select count (*) from " + c.getSimpleName());
        return (Integer) query.getSingleResult();
    }
}
