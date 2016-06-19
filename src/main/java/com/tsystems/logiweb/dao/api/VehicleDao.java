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
    public void setAvailable(VehicleEntity vehicle, boolean isAvailable);
    public void setCity(VehicleEntity vehicle, String city);
    public void setOrder(VehicleEntity vehicle, OrderEntity order);
    public void deleteByVin(String vin);

    public void updateVehicle(VehicleEntity vehicle, String vin, String capacity, String numberOfDrivers, String isAvailable,
                              CityEntity city, OrderEntity order);
}
