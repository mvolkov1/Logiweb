package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.TransactionManager;
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

    private TransactionManager transactionManager = TransactionManager.getInstance();
    private DistanceDao distanceDao = new DistanceDaoImpl(transactionManager.getEntityManager());

    public synchronized List<DistanceEntity> getListOfDistances(){
        return distanceDao.getAllEntities(DistanceEntity.class);
    }

    public void addDistance(String city1, String city2, float distance)
    {
        try {
            transactionManager.beginTransaction();
            try {
                CityDao cityDao = new CityDaoImpl(transactionManager.getEntityManager());

                DistanceEntity distanceEntity = new DistanceEntity();
                distanceEntity.setCity1(cityDao.findByName(city1));
                distanceEntity.setCity2(cityDao.findByName(city2));
                distanceEntity.setDistance(new BigDecimal(distance));
                distanceDao.save(distanceEntity);
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
}
