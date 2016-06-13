package com.tsystems.logiweb.dao.api;

import com.tsystems.logiweb.dao.entity.DistanceEntity;

/**
 * Created by mvolkov on 13.06.2016.
 */
public interface DistanceDao {
    public void save(DistanceEntity distanceEntity);
    public DistanceEntity findDistance(String city1, String city2);
}
