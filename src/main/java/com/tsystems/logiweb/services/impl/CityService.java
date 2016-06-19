package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.TransactionManager;
import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.impl.CityDaoImpl;

import java.util.List;

/**
 * Created by mvolkov on 17.06.2016.
 */
public class CityService {

    private TransactionManager transactionManager = TransactionManager.getInstance();
    private CityDao cityDao = new CityDaoImpl(transactionManager.getEntityManager());

    public synchronized  List<CityEntity> getListOfCities(){
        return cityDao.getAllEntities(CityEntity.class);
    }

    public void addCity(String city)
    {
        try {
            transactionManager.beginTransaction();
            try {
                CityEntity cityEntity = new CityEntity();
                cityEntity.setCity(city);
                cityDao.save(cityEntity);
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

    public synchronized CityEntity getCityByName(String city){
        return cityDao.findByName(city);
    }

}
