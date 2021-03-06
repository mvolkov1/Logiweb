package com.tsystems.logiweb.dao.api;

import com.tsystems.logiweb.dao.entity.CargoEntity;

import java.util.List;


/**
 * Created by mvolkov on 13.06.2016.
 */
public interface CargoDao extends BaseDao<CargoEntity> {

    public List<CargoEntity> getCargosForOrder(String orderId);

}
