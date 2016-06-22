package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.CargoDao;
import com.tsystems.logiweb.dao.entity.CargoEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by mvolkov on 13.06.2016.
 */
public class CargoDaoImpl extends BaseDaoImpl<CargoEntity> implements CargoDao {

    public CargoDaoImpl(EntityManager entityManager)
    {
        super(entityManager);
    }

    public List<CargoEntity> getCargosForOrder(String orderId)
    {
        Query query = entityManager.createQuery(
                "select object(c) from CargoEntity c where c.order.uid = :orderId"
        );
        query.setParameter("orderId", orderId);
        List<CargoEntity> cargoEntities = null;
        try
        {
            cargoEntities = (List<CargoEntity>) query.getResultList();
        }
        catch (Exception e)
        {
            cargoEntities = null;
        }
        return cargoEntities;
    }

}
