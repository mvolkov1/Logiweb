package com.tsystems.logiweb.dao.api;

import com.tsystems.logiweb.dao.entity.UserEntity;

import java.util.List;

/**
 * Created by mvolkov on 19.06.2016.
 */
public interface UserDao extends BaseDao<UserEntity> {
    public UserEntity findByLogin(String name);
    public List<UserEntity> getListOfDrivers();
    public List<UserEntity> getListOfManagers();
}
