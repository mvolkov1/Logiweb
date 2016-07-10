package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.services.TransactionManager;
import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.api.VehicleDao;
import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.VehicleEntity;
import com.tsystems.logiweb.dao.impl.CityDaoImpl;
import com.tsystems.logiweb.dao.impl.VehicleDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by mvolkov on 27.05.2016.
 */
public class VehicleService extends  BaseService {


    public void createVehicle(String vin, float capacity, int numberOfDrivers, short isAvailable, String city) {

        try {
            TransactionManager.beginTransaction();
            VehicleEntity vehicleEntity = new VehicleEntity();
            vehicleEntity.setVin(vin);
            vehicleEntity.setCapacity(new BigDecimal(capacity));
            vehicleEntity.setNumberOfDrivers(numberOfDrivers);
            vehicleEntity.setCity(cityDao.findByName(city));
            vehicleEntity.setIsAvailable(isAvailable);
            vehicleDao.save(vehicleEntity);
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }


    public void saveVehicle(String vin, String capacity, String numberOfDrivers, String city, String isAvailable) {

        if (capacity != null && numberOfDrivers != null && city != null && isAvailable != null) {

            try {
                TransactionManager.beginTransaction();

                VehicleEntity vehicleEntity = vehicleDao.findByVin(vin);
                if (vehicleEntity == null) {
                    vehicleEntity = new VehicleEntity();
                    vehicleEntity.setVin(vin);
                }
                CityEntity cityEntity = cityDao.findByName(city);
                vehicleEntity.setCity(cityEntity);
                vehicleEntity.setCapacity(new BigDecimal(capacity));
                vehicleEntity.setNumberOfDrivers(Integer.parseInt(numberOfDrivers));
                vehicleEntity.setIsAvailable(Short.parseShort(isAvailable));
                vehicleDao.save(vehicleEntity);
                TransactionManager.commit();
            } catch (Exception e) {
                TransactionManager.rollback();
            }
        }
    }

    public List<VehicleEntity> getListOfVehicles() {
        List<VehicleEntity> vehicles = null;
        try {
            TransactionManager.beginTransaction();

            vehicles = vehicleDao.getAllEntities(VehicleEntity.class);
            TransactionManager.commit();
        } catch (Exception e) {
            vehicles = null;
        }
        return vehicles;
    }

    public void deleteVehicle(String vin) {
        try {
            TransactionManager.beginTransaction();
           vehicleDao.deleteByVin(vin);
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

    public VehicleEntity getVehicle(String vin) {
        VehicleEntity vehicle = null;
        try {
            TransactionManager.beginTransaction();
            vehicle = vehicleDao.findByVin(vin);
            TransactionManager.commit();
        } catch (Exception e) {
            vehicle = null;
        }
        return vehicle;
    }
}


