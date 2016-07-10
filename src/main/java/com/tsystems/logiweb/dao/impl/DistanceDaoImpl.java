package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.DistanceDao;
import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.DistanceEntity;

import javax.persistence.*;

/**
 * Created by mvolkov on 13.06.2016.
 */
public class DistanceDaoImpl extends BaseDaoImpl<DistanceEntity> implements DistanceDao{

    public DistanceDaoImpl(EntityManager entityManager)
    {
        super(entityManager);
    }



    public DistanceEntity findById(long id) {
        return entityManager.find(DistanceEntity.class, id);
    }

    public DistanceEntity findDistance(CityEntity city1, CityEntity city2){

        Query query = entityManager.createQuery(
                "select object(d) from DistanceEntity d where d.city1 = :city1 and d.city2 = :city2"
        );
        query.setParameter("city1", city1);
        query.setParameter("city2", city2);
        DistanceEntity distanceEntity = null;
        try
        {
            distanceEntity = (DistanceEntity) query.getSingleResult();
        }
        catch (Exception e)
        {
            distanceEntity = null;
        }
        return distanceEntity;
    }

}
