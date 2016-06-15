package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.DriverDao;
import com.tsystems.logiweb.dao.entity.DriverEntity;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by mvolkov on 13.06.2016.
 */
public class DriverDaoImpl extends BaseDao implements DriverDao {
    public void save(DriverEntity driverEntity)
    {
        super.save(driverEntity);
    }

    public List<DriverEntity> getAllDrivers() {
        Query query = em.createQuery("select object(d) from DriverEntity d");
        return (List<DriverEntity>) query.getResultList();
    }
}
