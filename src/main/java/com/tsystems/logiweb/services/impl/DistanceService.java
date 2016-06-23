package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.services.TransactionManager;
import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.api.DistanceDao;
import com.tsystems.logiweb.dao.entity.DistanceEntity;
import com.tsystems.logiweb.dao.impl.CityDaoImpl;
import com.tsystems.logiweb.dao.impl.DistanceDaoImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by mvolkov on 18.06.2016.
 */
public class DistanceService {

    private DistanceDao distanceDao = new DistanceDaoImpl(TransactionManager.getEntityManager());

    public void addDistance(String city1, String city2, float distance) {
        try {
            TransactionManager.beginTransaction();

            CityDao cityDao = new CityDaoImpl(TransactionManager.getEntityManager());

            DistanceEntity distanceEntity = new DistanceEntity();
            distanceEntity.setCity1(cityDao.findByName(city1));
            distanceEntity.setCity2(cityDao.findByName(city2));
            distanceEntity.setDistance(new BigDecimal(distance));
            distanceDao.save(distanceEntity);
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }
}
