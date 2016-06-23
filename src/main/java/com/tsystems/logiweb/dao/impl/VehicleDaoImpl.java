package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.VehicleDao;
import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.VehicleEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;


/**
 * Created by mvolkov on 27.05.2016.
 */
public class VehicleDaoImpl extends BaseDaoImpl<VehicleEntity> implements VehicleDao {

    public VehicleDaoImpl(EntityManager entityManager)
    {
        super(entityManager);
    }

    public VehicleEntity findById(String id) {
        return entityManager.find(VehicleEntity.class, id);
    }

    public VehicleEntity findByVin(String vin){

        Query query = entityManager.createQuery(
                "select object(v) from VehicleEntity v where v.vin = :vin"
        );
        query.setParameter("vin", vin);
        VehicleEntity vehicleEntity = null;
        try
        {
            vehicleEntity = (VehicleEntity) query.getSingleResult();
        }
        catch (Exception e)
        {
            vehicleEntity = null;
        }
        return vehicleEntity;
    }

    public void deleteByVin(String vin)
    {
        VehicleEntity vehicleEntity = findByVin(vin);
        entityManager.remove(vehicleEntity);
    }


    public void setAvailable(VehicleEntity vehicle, boolean isAvailable) {
        Query query = entityManager.createQuery("update VehicleEntity v set v.isAvailable = :b where v.id = :id");
        query.setParameter("b", isAvailable ? 1 : 0);
        query.setParameter("id", vehicle.getId());
        try {
            query.executeUpdate();
        } catch (Exception e) {
          //  throw new LogiwebDaoException("adsfads", e);
        }
    }

    public void setCity(VehicleEntity vehicle, String city){
        Query query = entityManager.createQuery("update VehicleEntity v set v.city = :city where v.id = :id");
        query.setParameter("city", city);
        query.setParameter("id", vehicle.getId());
        query.executeUpdate();
    }

    public void setOrder(VehicleEntity vehicle, OrderEntity order) {
        Query query = entityManager.createQuery("update VehicleEntity v set v.order = :order where v.id = :id");
        query.setParameter("order", order);
        query.setParameter("id", vehicle.getId());
        query.executeUpdate();
    }

    public void setCapacity(VehicleEntity vehicle, String capacity){
        Query query = entityManager.createQuery("update VehicleEntity v set v.capacity = :capacity where v.id = :id");
        query.setParameter("capacity", capacity);
        query.setParameter("id", vehicle.getId());
        query.executeUpdate();
    }

    public void setNumberOfDrivers(VehicleEntity vehicle, String numberOfDrivers){
        Query query = entityManager.createQuery("update VehicleEntity v set v.numberOfDrivers = :numberOfDrivers where v.id = :id");
        query.setParameter("numberOfDrivers", numberOfDrivers);
        query.setParameter("id", vehicle.getId());
        query.executeUpdate();
    }

    public void updateVehicle(VehicleEntity vehicle, String vin, String capacity, String numberOfDrivers, String isAvailable,
                              CityEntity city, OrderEntity order)
    {
        Query query = entityManager.createQuery("update VehicleEntity v"
                + " set v.vin = :vin, "
                + " v.capacity= :capacity, "
                + " v.numberOfDrivers = :numberOfDrivers, "
                + " v.isAvailable = :isAvailable, "
                + " v.city = :city, "
                + " v.order = :order "
                + " where v.id = :id");
        query.setParameter("id", vehicle.getId());
        query.setParameter("vin", vin);
        query.setParameter("capacity", new BigDecimal(capacity));
        query.setParameter("numberOfDrivers", Integer.parseInt(numberOfDrivers));
        query.setParameter("isAvailable", Short.parseShort(isAvailable));
        query.setParameter("city", city);
        query.setParameter("order", order);

        int updateCount = 0;
        try
        {
            updateCount = query.executeUpdate();
        }
        catch (Exception e)
        {

        }
    }

    public void setOrderForVehicle(VehicleEntity vehicleEntity, OrderEntity orderEntity)
    {
        Query query = entityManager.createQuery("update VehicleEntity v"
                + " set v.order = :order "
                + " where v.id = :id");
        query.setParameter("id", vehicleEntity.getId());
        query.setParameter("order", orderEntity);

        int updateCount = 0;
        try
        {
            updateCount = query.executeUpdate();
        }
        catch (Exception e)
        {

        }
    }

    public List<VehicleEntity> getListOfVehiclesForOrder(String startCity, short capacity)
    {
        List<VehicleEntity> vehicles = null;
        Query query = entityManager.createQuery("select object(v) from VehicleEntity v where ( v.order = null and v.isAvailable=1 and v.capacity > :capacity) ");
        query.setParameter("capacity", capacity);
        try {

            vehicles = (List<VehicleEntity>) query.getResultList();
        }
        catch (Exception e)
        {
            vehicles = null;
        }

        return vehicles;
    }

}
