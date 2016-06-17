package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.VehicleDao;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.VehicleEntity;

import javax.persistence.Query;


/**
 * Created by mvolkov on 27.05.2016.
 */
public class VehicleDaoImpl extends BaseDaoImpl<VehicleEntity> implements VehicleDao {



    public VehicleEntity findById(String id) {
        return em.find(VehicleEntity.class, id);
    }

    public VehicleEntity findByVin(String vin){

        Query query = em.createQuery(
                "select object(v) from VehicleEntity v where v.vin = :vin"
        );
        query.setParameter("vin", vin);
        return (VehicleEntity) query.getSingleResult();
    }


    public void setAvailable(VehicleEntity vehicle, boolean isAvailable) {
        Query query = em.createQuery("update VehicleEntity v set v.isAvailable = :b where v.id = :id");
        query.setParameter("b", isAvailable ? 1 : 0);
        query.setParameter("id", vehicle.getId());
        tr.begin();
        query.executeUpdate();
        tr.commit();

    }

    public void setCity(VehicleEntity vehicle, String city){
        Query query = em.createQuery("update VehicleEntity v set v.city = :city where v.id = :id");
        query.setParameter("city", city);
        query.setParameter("id", vehicle.getId());
        tr.begin();
        query.executeUpdate();
        tr.commit();
    }

    public void setOrder(VehicleEntity vehicle, OrderEntity order) {
        Query query = em.createQuery("update VehicleEntity v set v.order = :order where v.id = :id");
        query.setParameter("order", order);
        query.setParameter("id", vehicle.getId());
        tr.begin();
        query.executeUpdate();
        tr.commit();
    }


}
