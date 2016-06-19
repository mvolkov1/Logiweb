package com.tsystems.logiweb.dao.api;

import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.DriverEntity;
import com.tsystems.logiweb.dao.entity.OrderEntity;


/**
 * Created by mvolkov on 13.06.2016.
 */
public interface DriverDao extends BaseDao<DriverEntity> {
    public DriverEntity findByUid(String uid);
    public void deleteByUid(String uid);
    public void updateDriver(DriverEntity driver, String uid, String monthHours, String status,
                              CityEntity city, OrderEntity order);
}
