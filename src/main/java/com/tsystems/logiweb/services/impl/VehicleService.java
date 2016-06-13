package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.entity.VehicleEntity;
import com.tsystems.logiweb.dao.impl.VehicleDaoImpl;

/**
 * Created by mvolkov on 27.05.2016.
 */
public class VehicleService {

    private VehicleDaoImpl vehicleDao = new VehicleDaoImpl();

    public void save(VehicleEntity vehicle) {
        vehicleDao.save(vehicle);
    }
}
