package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.entity.VehicleEntity;
import com.tsystems.logiweb.dao.impl.VehicleDaoImpl;

import java.util.List;

/**
 * Created by mvolkov on 27.05.2016.
 */
public class VehicleService {

    private VehicleDaoImpl vehicleDao = new VehicleDaoImpl();

    public void addVehicle(VehicleEntity vehicle) {
        vehicleDao.save(vehicle);
    }


    public List<VehicleEntity> getListOfVehicles(){
        return vehicleDao.getAllVehicles();
    }

}
