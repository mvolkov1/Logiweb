package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.services.TransactionManager;
import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.api.DriverDao;
import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.DriverEntity;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.UserEntity;
import com.tsystems.logiweb.dao.impl.CityDaoImpl;
import com.tsystems.logiweb.dao.impl.DriverDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by mvolkov on 16.06.2016.
 */
public class DriverService extends BaseService {


    public void addDriver(String login, String password, String firstName, String lastName,
                          String uid, int monthHours, String status, String city) {
        try {
            TransactionManager.beginTransaction();
            DriverEntity driverEntity = new DriverEntity();
            driverEntity.setUid(uid);
            driverEntity.setMonthHours(monthHours);
            driverEntity.setStatus(status);
            driverEntity.setCity(cityDao.findByName(city));

            UserEntity userEntity = new UserEntity();
            userEntity.setLogin(login);
            userEntity.setPassword(password);
            userEntity.setRole("driver");
            userEntity.setFirstName(firstName);
            userEntity.setLastName(lastName);

            driverEntity.setUser(userEntity);

            driverDao.save(driverEntity);
            TransactionManager.commit();

        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

    public List<DriverEntity> getListOfDrivers() {

        List<DriverEntity> drivers = null;
        try {
            TransactionManager.beginTransaction();
            drivers = driverDao.getAllEntities(DriverEntity.class);
            TransactionManager.commit();
        } catch (Exception e) {
            drivers = null;
        }
        return drivers;
    }

    public DriverEntity getDriver(String uid) {
        DriverEntity driver = null;
        try {
            TransactionManager.beginTransaction();
            driver = driverDao.findByUid(uid);
            TransactionManager.commit();
        } catch (Exception e) {
            driver = null;
        }
        return driver;
    }

    public void saveDriver(String uid, String firstName, String lastName, String login, String password,
                           String oldUid, String monthHours, String status, String city) {
        if (monthHours != null && status != null && city != null) {
            try {
                TransactionManager.beginTransaction();
                DriverEntity driverEntity = driverDao.findByUid(oldUid);
                if (driverEntity == null) {
                    UserEntity user = new UserEntity();
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setRole("driver");
                    user.setLogin(login);
                    user.setPassword(password);
                    driverEntity = new DriverEntity();
                    driverEntity.setUid(uid);
                    driverEntity.setUser(user);
                }
                CityEntity cityEntity = cityDao.findByName(city);
                driverEntity.setUid(uid);
                driverEntity.setCity(cityEntity);
                driverEntity.setStatus(status);
                driverEntity.setMonthHours(Integer.parseInt(monthHours));
                driverDao.save(driverEntity);
                TransactionManager.commit();

            } catch (Exception e) {
                TransactionManager.rollback();
            }
        }
    }

    public void deleteDriver(String uid) {
        try {
            TransactionManager.beginTransaction();
            driverDao.deleteByUid(uid);
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

}


