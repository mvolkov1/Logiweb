package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.TransactionManager;
import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.api.DriverDao;
import com.tsystems.logiweb.dao.api.UserDao;
import com.tsystems.logiweb.dao.entity.DriverEntity;
import com.tsystems.logiweb.dao.entity.UserEntity;
import com.tsystems.logiweb.dao.impl.CityDaoImpl;
import com.tsystems.logiweb.dao.impl.DriverDaoImpl;
import com.tsystems.logiweb.dao.impl.UserDaoImpl;

import java.util.List;

/**
 * Created by mvolkov on 19.06.2016.
 */
public class UserService {


    private TransactionManager transactionManager = TransactionManager.getInstance();
    private UserDao userDao = new UserDaoImpl(transactionManager.getEntityManager());

    public synchronized List<UserEntity> getListOfUsers(){
        return userDao.getAllEntities(UserEntity.class);
    }

    public UserEntity findUserByLogin(String login)
    {
        return userDao.findByLogin(login);
    }


//    public synchronized List<UserEntity> getListOfDrivers(){
//        return userDao.getListOfDrivers();
//    }
//
//    public synchronized List<UserEntity> getListOfManagers(){
//        return userDao.getListOfManagers();
//    }

    private void addUser(String login, String password, String role, String firstName, String lastName)
    {
        try {
            transactionManager.beginTransaction();
            try {
                UserEntity userEntity = new UserEntity();
                userEntity.setLogin(login);
                userEntity.setPassword(password);
                userEntity.setRole(role);
                userEntity.setFirstName(firstName);
                userEntity.setLastName(lastName);
                userDao.save(userEntity);
                transactionManager.commitTransaction();
            }
            catch (Exception e)
            {
                transactionManager.rollbackTransaction();
            }
        }
        catch (Exception e)
        {}
    }

    public synchronized void addManager(String login, String password, String role, String firstName, String lastName)
    {
        addUser(login, password, role, firstName, lastName);
    }

//    public synchronized void addDriver(String login, String password, String firstName, String lastName,
//                                       String uid, int monthHours, String status, String city)
//    {
//        try {
//            transactionManager.beginTransaction();
//            try {
//                CityDao cityDao = new CityDaoImpl(transactionManager.getEntityManager());
//                DriverDao driverDao = new DriverDaoImpl(transactionManager.getEntityManager());
//                UserEntity userEntity = new UserEntity();
//                userEntity.setLogin(login);
//                userEntity.setPassword(password);
//                userEntity.setRole("driver");
//                userEntity.setFirstName(firstName);
//                userEntity.setLastName(lastName);
//                DriverEntity driverEntity = new DriverEntity();
//                driverEntity.setUid(uid);
//                driverEntity.setMonthHours(monthHours);
//                driverEntity.setStatus(status);
//                driverEntity.setCity(cityDao.findByName(city));
//                driverEntity.setUser(userEntity);
//                driverDao.save(driverEntity);
//                transactionManager.commitTransaction();
//            }
//            catch (Exception e)
//            {
//                transactionManager.rollbackTransaction();
//            }
//        }
//        catch (Exception e)
//        {}
//    }
}
