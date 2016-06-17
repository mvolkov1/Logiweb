package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.DistanceDao;
import com.tsystems.logiweb.dao.entity.DistanceEntity;

import javax.persistence.*;

/**
 * Created by mvolkov on 13.06.2016.
 */
public class DistanceDaoImpl extends BaseDaoImpl<DistanceEntity> implements DistanceDao{



//    public void save(DistanceEntity distanceEntity) {
//        super.save(distanceEntity);
//    }

    public DistanceEntity findById(long id) {
        return em.find(DistanceEntity.class, id);
    }

    public DistanceEntity findDistance(String city1, String city2){

        Query query = em.createQuery(
                "select object(d) from DistanceEntity d where d.city1 = :city1 and d.city2 = :city2"
        );
        query.setParameter("city1", city1);
        query.setParameter("city2", city2);
        return (DistanceEntity) query.getSingleResult();
    }

}
