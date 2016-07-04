package com.tsystems.logiweb.dao.api;

import com.tsystems.logiweb.dao.entity.OrderEntity;

/**
 * Created by mvolkov on 13.06.2016.
 */
public interface OrderDao extends BaseDao<OrderEntity> {
    public OrderEntity findByUid(String orderId);
    public void updateOrder(OrderEntity orderEntity, String uid, String numberOfItems, String isCompleted);

}
