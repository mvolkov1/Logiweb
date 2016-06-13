package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.OrderDao;
import com.tsystems.logiweb.dao.entity.OrderEntity;


/**
 * Created by mvolkov on 13.06.2016.
 */
public class OrderDaoImpl extends BaseDao implements OrderDao{

    public void save(OrderEntity order) {
        super.save(order);
    }
}
