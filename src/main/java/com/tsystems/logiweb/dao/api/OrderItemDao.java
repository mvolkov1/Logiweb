package com.tsystems.logiweb.dao.api;

import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.OrderItemEntity;
import com.tsystems.logiweb.dao.impl.OrderItemDaoImpl;

import java.util.List;


/**
 * Created by mvolkov on 13.06.2016.
 */
public interface OrderItemDao extends BaseDao<OrderItemEntity>{

    public List<OrderItemEntity> getItemsForOrder(String orderId);
}
