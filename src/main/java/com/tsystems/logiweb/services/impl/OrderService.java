package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.api.OrderDao;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.impl.OrderDaoImpl;

import java.util.List;

/**
 * Created by mvolkov on 17.06.2016.
 */
public class OrderService {

    private OrderDao orderDao = new OrderDaoImpl();

    public List<OrderEntity> getListOfOrders(){
        return orderDao.getAllEntities(OrderEntity.class);
    }
}
