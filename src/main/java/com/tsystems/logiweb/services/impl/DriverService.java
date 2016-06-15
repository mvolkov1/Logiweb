package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.entity.DriverEntity;
import com.tsystems.logiweb.dao.impl.DriverDaoImpl;

import java.util.List;

/**
 * Created by mvolkov on 16.06.2016.
 */
public class DriverService {

    private DriverDaoImpl driverDao = new DriverDaoImpl();

    public void addVehicle(DriverEntity driverEntity) {
        driverDao.save(driverEntity);
    }

    public List<DriverEntity> getListOfDrivers(){
        return driverDao.getAllDrivers();
    }
}
