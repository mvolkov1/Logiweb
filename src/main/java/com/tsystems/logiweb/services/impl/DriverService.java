package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.TransactionManager;
import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.api.DriverDao;
import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.DriverEntity;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.UserEntity;
import com.tsystems.logiweb.dao.impl.CityDaoImpl;
import com.tsystems.logiweb.dao.impl.DriverDaoImpl;

import java.util.List;

/**
 * Created by mvolkov on 16.06.2016.
 */
public class DriverService {

    private TransactionManager transactionManager = TransactionManager.getInstance();
    private DriverDao driverDao = new DriverDaoImpl(transactionManager.getEntityManager());

    public synchronized List<DriverEntity> getListOfDrivers(){
        return driverDao.getAllEntities(DriverEntity.class);
    }

//    public synchronized void addDriver(String uid, String firstName, String lastName,
//                                       int monthHours, String status, String city)
//    {
//        try {
//            transactionManager.beginTransaction();
//            try{
//                CityDao cityDao = new CityDaoImpl(transactionManager.getEntityManager());
//                DriverEntity driverEntity = new DriverEntity();
//                driverEntity.setUid(uid);
//                driverEntity.setMonthHours(monthHours);
//                driverEntity.setStatus(status);
//                driverEntity.setCity(cityDao.findByName(city));
//
//                driverDao.save(driverEntity);
//
//                transactionManager.commitTransaction();
//
//            } catch (Exception e) {
//
//                transactionManager.rollbackTransaction();
//                //TODO   throw new LogiwebServiceException("", e);
//            } finally {
////                transactionManager.close();
//            }
//        }
//        catch (Exception e)
//        {
//            // TODO throw new TransactionManagerException("",e)
//        }
//    }

    public synchronized DriverEntity getDriverByUid(String uid){
        return driverDao.findByUid(uid);
    }

    public synchronized void addDriver(String login, String password, String firstName, String lastName,
                                       String uid, int monthHours, String status, String city)
    {
        try {
            transactionManager.beginTransaction();
            try {


                DriverEntity driverEntity = new DriverEntity();
                driverEntity.setUid(uid);
                driverEntity.setMonthHours(monthHours);
                driverEntity.setStatus(status);
                driverEntity.setCity(new CityDaoImpl(transactionManager.getEntityManager()).findByName(city));

                UserEntity userEntity = new UserEntity();
                userEntity.setLogin(login);
                userEntity.setPassword(password);
                userEntity.setRole("driver");
                userEntity.setFirstName(firstName);
                userEntity.setLastName(lastName);

                driverEntity.setUser(userEntity);

                driverDao.save(driverEntity);
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

    public synchronized void editDriver(String uid, int monthHours, String status, String city)
    {
        try {
            transactionManager.beginTransaction();
            try{
                CityDao cityDao = new CityDaoImpl(transactionManager.getEntityManager());
                DriverEntity driverEntity = new DriverEntity();
                driverEntity.setUid(uid);
                driverEntity.setMonthHours(monthHours);
                driverEntity.setStatus(status);
                driverEntity.setCity(cityDao.findByName(city));

                driverDao.save(driverEntity);

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

    public synchronized void deleteDriver(String uid)
    {
        try {
            transactionManager.beginTransaction();
            try{
                driverDao.deleteByUid(uid);
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


    public synchronized void updateDriver(DriverEntity driverEntity, String uid, String monthHours, String status,
                                           CityEntity city, OrderEntity order)
    {
        try {
            transactionManager.beginTransaction();
            try{
                driverDao.updateDriver(driverEntity, uid, monthHours, status, city, order);
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
