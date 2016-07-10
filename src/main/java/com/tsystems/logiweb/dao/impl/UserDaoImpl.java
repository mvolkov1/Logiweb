package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.BaseDao;
import com.tsystems.logiweb.dao.api.UserDao;
import com.tsystems.logiweb.dao.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by mvolkov on 19.06.2016.
 */
public class UserDaoImpl extends BaseDaoImpl<UserEntity> implements UserDao {

    public UserDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public UserDaoImpl() {
        super();
    }

    public UserEntity findByLogin(String login) {
        UserEntity userEntity = null;
        Query query = entityManager.createQuery(
                "select object(c) from UserEntity c where c.login = :login"
        );
        query.setParameter("login", login);
        try {
            userEntity = (UserEntity) query.getSingleResult();
        } catch (Exception e) {
            userEntity = null;
        }
        return userEntity;
    }

    public List<UserEntity> getListOfDrivers() {
        Query query = entityManager.createQuery(
                "select object(u) from UserEntity u where u.role = :role"
        );
        query.setParameter("role", "driver");
        List<UserEntity> listOfDrivers = null;
        try {
            listOfDrivers = (List<UserEntity>) query.getResultList();
        } catch (Exception e) {
            listOfDrivers = null;
        }
        return listOfDrivers;
    }

    public List<UserEntity> getListOfManagers() {
        Query query = entityManager.createQuery(
                "select object(u) from UserEntity u where u.role = :role"
        );
        query.setParameter("role", "manager");
        List<UserEntity> listOfManagers = null;
        try {
            listOfManagers = (List<UserEntity>) query.getResultList();
        } catch (Exception e) {
            listOfManagers = null;
        }
        return listOfManagers;
    }
}
