package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.entity.CityEntity;

import javax.persistence.*;

/**
 * Created by mvolkov on 06.06.2016.
 */
public class CityDaoImpl extends BaseDaoImpl<CityEntity> implements CityDao {

    public CityDaoImpl(EntityManager entityManager)
    {
        super(entityManager);
    }

    public CityEntity findById(long id) {
        return entityManager.find(CityEntity.class, id);
    }

    public CityEntity findByName(String name) {

        Query query = entityManager.createQuery(
                "select object(c) from CityEntity c where c.city = :name"
        );
        query.setParameter("name", name);
        CityEntity cityEntity = null;
        try
        {
            cityEntity = (CityEntity) query.getSingleResult();
        }
        catch (Exception e)
        {
            cityEntity = null;
        }
        return cityEntity;
    }


}
