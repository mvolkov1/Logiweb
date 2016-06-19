package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.CargoDao;
import com.tsystems.logiweb.dao.entity.CargoEntity;

import javax.persistence.EntityManager;

/**
 * Created by mvolkov on 13.06.2016.
 */
public class CargoDaoImpl extends BaseDaoImpl<CargoEntity> implements CargoDao {

    public CargoDaoImpl(EntityManager entityManager)
    {
        super(entityManager);
    }

}
