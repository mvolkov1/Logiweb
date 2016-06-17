package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.entity.CityEntity;

import javax.persistence.*;

/**
 * Created by mvolkov on 06.06.2016.
 */
public class CityDaoImpl extends BaseDaoImpl<CityEntity> implements CityDao {



    public CityEntity findById(long id) {
        return em.find(CityEntity.class, id);
    }

    public CityEntity findByName(String name) {

        Query query = em.createQuery(
                "select object(c) from CityEntity c where c.city = :name"
        );
        query.setParameter("name", name);
        return (CityEntity) query.getSingleResult();
    }


}
