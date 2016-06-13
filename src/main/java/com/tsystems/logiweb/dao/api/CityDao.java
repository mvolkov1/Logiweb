package com.tsystems.logiweb.dao.api;

import com.tsystems.logiweb.dao.entity.CityEntity;

import java.util.List;

/**
 * Created by mvolkov on 13.06.2016.
 */
public interface CityDao {
    public void save(CityEntity cityEntity);
    public CityEntity findByName(String name);
    public List<CityEntity> getAllCities();
    public int getNumberOfCities();
}
