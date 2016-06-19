package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.DriverDao;
import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.DriverEntity;
import com.tsystems.logiweb.dao.entity.OrderEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by mvolkov on 13.06.2016.
 */
public class DriverDaoImpl extends BaseDaoImpl<DriverEntity> implements DriverDao {

    public DriverDaoImpl(EntityManager entityManager)
    {
        super(entityManager);
    }

    public DriverEntity findByUid(String uid)
    {
        Query query = entityManager.createQuery(
                "select object(d) from DriverEntity d where d.uid = :uid"
        );
        query.setParameter("uid", uid);
        DriverEntity driverEntity = null;
        try
        {
            driverEntity = (DriverEntity) query.getSingleResult();
        }
        catch (Exception e)
        {
            driverEntity = null;
        }
        return driverEntity;
    }

    public void deleteByUid(String uid)
    {
        DriverEntity driverEntity = findByUid(uid);
        entityManager.remove(driverEntity);
    }

    public void updateDriver(DriverEntity driver, String uid, String monthHours, String status,
                              CityEntity city, OrderEntity order)
    {
        Query query = entityManager.createQuery("update DriverEntity d"
                + " set d.uid = :uid, "
                + " d.monthHours= :monthHours, "
                + " d.status = :status, "
                + " d.city = :city, "
                + " d.order = :order "
                + " where d.id = :id");
        query.setParameter("id", driver.getId());
        query.setParameter("uid", uid);
        query.setParameter("monthHours", Integer.parseInt(monthHours));
        query.setParameter("status", status);
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


}
