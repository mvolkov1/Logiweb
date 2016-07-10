package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.api.*;
import com.tsystems.logiweb.dao.impl.*;
import com.tsystems.logiweb.services.TransactionManager;

import javax.persistence.EntityManager;

/**
 * Created by mvolkov on 10.07.2016.
 */
public class BaseService {

    protected OrderDao orderDao = new OrderDaoImpl();
    protected VehicleDao vehicleDao = new VehicleDaoImpl();
    protected DriverDao driverDao = new DriverDaoImpl();
    protected CityDao cityDao = new CityDaoImpl();
    protected DistanceDao distanceDao = new DistanceDaoImpl();
    protected UserDaoImpl userDao = new UserDaoImpl();

    public void setEntityManager(EntityManager entityManager) {
        orderDao.setEntityManager(entityManager);
        vehicleDao.setEntityManager(entityManager);
        driverDao.setEntityManager(entityManager);
        cityDao.setEntityManager(entityManager);
        distanceDao.setEntityManager(entityManager);
        userDao.setEntityManager(entityManager);
    }
}
