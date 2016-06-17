package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.api.OrderItemDao;
import com.tsystems.logiweb.dao.entity.OrderItemEntity;
import com.tsystems.logiweb.dao.impl.OrderItemDaoImpl;

import java.util.List;

/**
 * Created by mvolkov on 17.06.2016.
 */
public class OrderItemService {

    private OrderItemDao orderItemDao = new OrderItemDaoImpl();

    public List<OrderItemEntity> getListOfItems(String orderId){
        return orderItemDao.getItemsForOrder(orderId);
    }
}
