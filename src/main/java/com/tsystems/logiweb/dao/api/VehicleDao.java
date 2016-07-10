package com.tsystems.logiweb.dao.api;

import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.VehicleEntity;

import java.util.List;

/**
 * Created by mvolkov on 13.06.2016.
 */
public interface VehicleDao extends BaseDao<VehicleEntity>{
    public VehicleEntity findByVin(String name);
    public void deleteByVin(String vin);
}
