package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.TransactionManager;
import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.api.VehicleDao;
import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.VehicleEntity;
import com.tsystems.logiweb.dao.impl.CityDaoImpl;
import com.tsystems.logiweb.dao.impl.VehicleDaoImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by mvolkov on 27.05.2016.
 */
public class VehicleService {

    private TransactionManager transactionManager = TransactionManager.getInstance();
    private VehicleDao vehicleDao = new VehicleDaoImpl(transactionManager.getEntityManager());


    public synchronized List<VehicleEntity> getListOfVehicles(){
        return vehicleDao.getAllEntities(VehicleEntity.class);
    }

    public synchronized  List<VehicleEntity> getListOfVehiclesForOrder(String startCity, short capacity) {
        return vehicleDao.getListOfVehiclesForOrder(startCity, capacity);
    }


    public synchronized VehicleEntity getVehicleByVin(String vin){
        return vehicleDao.findByVin(vin);
    }


    public synchronized void addVehicle(String vin, float capacity, int numberOfDrivers, short isAvailable, String city)
    {
        try {
            transactionManager.beginTransaction();
            try{

                CityDao cityDao = new CityDaoImpl(transactionManager.getEntityManager());
                VehicleEntity vehicleEntity = new VehicleEntity();
                vehicleEntity.setVin(vin);
                vehicleEntity.setCapacity(new BigDecimal(capacity));
                vehicleEntity.setNumberOfDrivers(numberOfDrivers);
                vehicleEntity.setCity(cityDao.findByName(city));
                vehicleEntity.setIsAvailable(isAvailable);

                vehicleDao.save(vehicleEntity);

                transactionManager.commitTransaction();

            } catch (Exception e) {

                transactionManager.rollbackTransaction();
                //TODO   throw new LogiwebServiceException("", e);
            } finally {
//                transactionManager.close();
            }
        }
        catch (Exception e)
        {
            // TODO throw new TransactionManagerException("",e)
        }
    }

    public synchronized void updateVehicle(VehicleEntity vehicleEntity, String vin, String capacity, String numberOfDrivers, String isAvailable,
                                           CityEntity city, OrderEntity order)
    {
        try {
            transactionManager.beginTransaction();
            try{
                vehicleDao.updateVehicle(vehicleEntity, vin, capacity, numberOfDrivers, isAvailable, city, order);
                transactionManager.commitTransaction();

            } catch (Exception e) {

                transactionManager.rollbackTransaction();
                //TODO   throw new LogiwebServiceException("", e);
            } finally {
//                transactionManager.close();
            }
        }
        catch (Exception e)
        {
            // TODO throw new TransactionManagerException("",e)
        }
    }

    public synchronized void deleteVehicle(String vin)
    {
        try {
            transactionManager.beginTransaction();
            try{
                vehicleDao.deleteByVin(vin);
                transactionManager.commitTransaction();
            } catch (Exception e) {

                transactionManager.rollbackTransaction();
                //TODO   throw new LogiwebServiceException("", e);
            } finally {
//                transactionManager.close();
            }
        }
        catch (Exception e)
        {
            // TODO throw new TransactionManagerException("",e)
        }
    }

}
