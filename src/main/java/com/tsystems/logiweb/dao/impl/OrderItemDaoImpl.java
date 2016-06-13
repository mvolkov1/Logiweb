package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.OrderItemDao;
import com.tsystems.logiweb.dao.entity.OrderItemEntity;

/**
 * Created by mvolkov on 13.06.2016.
 */
public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {
    public void save(OrderItemEntity orderItemEntity)
    {
        super.save(orderItemEntity);
    }
}
