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
public class DriverService {


    public static void addDriver(String login, String password, String firstName, String lastName,
                                 String uid, int monthHours, String status, String city) {

        DriverDao driverDao = new DriverDaoImpl(TransactionManager.getEntityManager());

        try {
            TransactionManager.beginTransaction();
            DriverEntity driverEntity = new DriverEntity();
            driverEntity.setUid(uid);
            driverEntity.setMonthHours(monthHours);
            driverEntity.setStatus(status);
            driverEntity.setCity(new CityDaoImpl(TransactionManager.getEntityManager()).findByName(city));

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


    public static List<DriverEntity> getListOfDrivers() {

        List<DriverEntity> drivers = null;
        try {
            TransactionManager.beginTransaction();
            drivers = new DriverDaoImpl(TransactionManager.getEntityManager()).getAllEntities(DriverEntity.class);
            TransactionManager.commit();
        }
        catch (Exception e)
        {
            drivers = null;
        }
        return drivers;
    }

    public static DriverEntity getDriver(String uid) {
        DriverEntity driver = null;
        try {
            TransactionManager.beginTransaction();
            driver = new DriverDaoImpl(TransactionManager.getEntityManager()).findByUid(uid);
            TransactionManager.commit();
        }
        catch (Exception e)
        {
            driver = null;
        }
        return driver;
    }

    public static void saveDriver(String uid, String oldUid, String monthHours, String status, String city) {
        DriverDao driverDao = new DriverDaoImpl(TransactionManager.getEntityManager());
        if (oldUid != null && !oldUid.isEmpty() && monthHours != null && status != null && city != null) {
            try {
                TransactionManager.beginTransaction();
                DriverEntity driverEntity = driverDao.findByUid(oldUid);
                if (driverEntity != null) {
                    CityEntity cityEntity = new CityDaoImpl(TransactionManager.getEntityManager()).findByName(city);
                    driverEntity.setUid(uid);
                    driverEntity.setCity(cityEntity);
                    driverEntity.setStatus(status);
                    driverEntity.setMonthHours(Integer.parseInt(monthHours));
                    driverDao.save(driverEntity);
                    TransactionManager.commit();
                }
            } catch (Exception e) {
                TransactionManager.rollback();
            }
        }
    }
}


