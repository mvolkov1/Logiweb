package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.entity.DistanceEntity;
import com.tsystems.logiweb.dao.impl.BaseDaoImpl;
import com.tsystems.logiweb.services.TransactionManager;
import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.impl.CityDaoImpl;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by mvolkov on 17.06.2016.
 */
public class CityService extends BaseService {

    public void addCity(String city) {
        if (city != null && !city.isEmpty()) {
            try {
                TransactionManager.beginTransaction();
                CityEntity cityEntity = new CityEntity();
                cityEntity.setName(city);
                cityDao.save(cityEntity);
                TransactionManager.commit();
            } catch (Exception e) {
                TransactionManager.rollback();
            }
        }
    }

    public List<CityEntity> getListOfCities() {
        return cityDao.getAllEntities(CityEntity.class);
    }

    public Map<String, BigDecimal> getListOfNeighborCities(String name) {
        try {
            TransactionManager.beginTransaction();
            Map<String, BigDecimal> neighbors = new HashMap<>();
            List<DistanceEntity> distances = distanceDao.getAllEntities(DistanceEntity.class);
            for (DistanceEntity distance : distances) {
                if (distance.getCity1().getName().equals(name)) {
                    neighbors.put(distance.getCity2().getName(), distance.getDistance());
                }
                if (distance.getCity2().getName().equals(name)) {
                    neighbors.put(distance.getCity1().getName(), distance.getDistance());
                }
            }
            TransactionManager.commit();
            return neighbors;
        } catch (Exception e) {
            TransactionManager.rollback();
        }
        return null;
    }

    public Set<CityEntity> getListOfPossibleNeighbors(String cityName) {
        try {
            TransactionManager.beginTransaction();
            CityEntity city = cityDao.findByName(cityName);
            this.loadNeighbors(cityName);
            Set<CityEntity> cities = new HashSet<>(this.getListOfCities());
            cities.removeAll(city.getNeighbors());
            cities.remove(city);
            TransactionManager.commit();
            return cities;
        } catch (Exception e) {
            TransactionManager.rollback();
        }
        return null;
    }

    public void loadNeighbors(String cityName) {
        List<DistanceEntity> distances = distanceDao.getAllEntities(DistanceEntity.class);
        CityEntity city = cityDao.findByName(cityName);
        for (DistanceEntity distance : distances) {
            if (distance.getCity1().getName().equals(cityName)) {
                city.getNeighbors().add(distance.getCity2());
            }
            if (distance.getCity2().getName().equals(cityName)) {
                city.getNeighbors().add(distance.getCity1());
            }
        }
    }
}
