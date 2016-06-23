package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.services.TransactionManager;
import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.impl.CityDaoImpl;

import java.util.List;

/**
 * Created by mvolkov on 17.06.2016.
 */
public class CityService {

    private CityDao cityDao = new CityDaoImpl(TransactionManager.getEntityManager());

    public void addCity(String city) {
        try {
            TransactionManager.beginTransaction();
            CityEntity cityEntity = new CityEntity();
            cityEntity.setCity(city);
            cityDao.save(cityEntity);
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }

    }
}
