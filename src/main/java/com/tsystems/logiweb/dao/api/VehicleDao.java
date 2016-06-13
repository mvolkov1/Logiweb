package com.tsystems.logiweb.dao.api;

import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.VehicleEntity;

import java.util.List;

/**
 * Created by mvolkov on 13.06.2016.
 */
public interface VehicleDao {
    public void save(VehicleEntity vehicleEntity);
    public VehicleEntity findByVin(String name);
    public List<VehicleEntity> getAllVehicles();
    public int getNumberOfVehicles();
    public void setAvailable(VehicleEntity vehicle, boolean isAvailable);
    public void setCity(VehicleEntity vehicle, String city);
    public void setOrder(VehicleEntity vehicle, OrderEntity order);
}
