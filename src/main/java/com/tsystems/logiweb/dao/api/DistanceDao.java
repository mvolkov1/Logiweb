package com.tsystems.logiweb.dao.api;

import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.DistanceEntity;

/**
 * Created by mvolkov on 13.06.2016.
 */
public interface DistanceDao extends BaseDao<DistanceEntity>{
    public DistanceEntity findDistance(CityEntity city1, CityEntity city2);
}
