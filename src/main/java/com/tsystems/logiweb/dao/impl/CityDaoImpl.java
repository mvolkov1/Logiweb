package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.entity.CityEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by mvolkov on 06.06.2016.
 */
public class CityDaoImpl extends  BaseDao implements CityDao{

    public void save(CityEntity city) {
        super.save(city);
    }

    public CityEntity findById(long id) {
        return em.find(CityEntity.class, id);
    }

    public CityEntity findByName(String name){

        Query query = em.createQuery(
                "select object(c) from CityEntity c where c.city = :name"
        );
        query.setParameter("name", name);
        return (CityEntity) query.getSingleResult();
    }

    public List<CityEntity> getAllCities() {
        Query query = em.createQuery("select object(c) from CityEntity c");
        return (List<CityEntity>) query.getResultList();
    }

    public int getNumberOfCities() {
        Query query = em.createQuery("SELECT COUNT (*) FROM CityEntity");
        return (Integer) query.getSingleResult();
    }
}
