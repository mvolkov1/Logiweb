package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.VehicleDao;
import com.tsystems.logiweb.dao.entity.*;
import com.tsystems.logiweb.services.TransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mvolkov on 27.05.2016.
 */
public class VehicleDaoImpl extends BaseDaoImpl<VehicleEntity> implements VehicleDao {

    public VehicleDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }


    public VehicleEntity findByVin(String vin) {

        Query query = entityManager.createQuery(
                "select object(v) from VehicleEntity v where v.vin = :vin"
        );
        query.setParameter("vin", vin);
        VehicleEntity vehicleEntity = null;
        try {
            vehicleEntity = (VehicleEntity) query.getSingleResult();
        } catch (Exception e) {
            vehicleEntity = null;
        }
        return vehicleEntity;
    }

    public void deleteByVin(String vin) {
        VehicleEntity vehicleEntity = findByVin(vin);
        try {
            this.remove(vehicleEntity);
        } catch (Exception e) {
            throw e;
        }
    }


    public List<VehicleEntity> getListOfVehiclesForOrder(OrderEntity orderEntity) {

        VehicleDao vehicleDao = new VehicleDaoImpl(TransactionManager.getEntityManager());
        List<VehicleEntity> vehiclesForOrder = new ArrayList<VehicleEntity>();
        List<OrderItemEntity> items = (List<OrderItemEntity>) orderEntity.getItems();
        List<CargoEntity> cargos = (List<CargoEntity>) orderEntity.getCargos();
        List<VehicleEntity> vehicles = vehicleDao.getAllEntities(VehicleEntity.class);

        for (VehicleEntity v : vehicles) {
            if (v.getOrder() != null) continue;
            boolean isGood = true;

            for (OrderItemEntity oi : orderEntity.getItems()) {

                BigDecimal massAtItem = new BigDecimal(0);
                for (CargoEntity c : cargos) {
                    if (c.getItemStart().getCity().equals(oi.getCity().getName())) {
                        massAtItem = massAtItem.add(c.getMass());
                    }
                }

                if (massAtItem.compareTo(v.getCapacity()) > 0) {
                    isGood = false;
                    break;
                }
            }

            if (isGood) {
                vehiclesForOrder.add(v);
            }
        }
        return vehiclesForOrder;
    }

}
