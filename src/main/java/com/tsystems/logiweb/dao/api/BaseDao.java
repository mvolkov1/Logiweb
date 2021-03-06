package com.tsystems.logiweb.dao.api;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by mvolkov on 17.06.2016.
 */
public interface BaseDao<T> {

    public void save(T t);
    public void refresh(T t);
    public List<T> getAllEntities(Class c);
    public int getNumberOfEntities(Class c);
    public void setEntityManager(EntityManager entityManager);
    public void merge(T t);
    public void remove(T t);
    public void flush();
}
