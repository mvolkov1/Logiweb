package com.tsystems.logiweb.dao.impl;

import com.tsystems.logiweb.dao.api.OrderItemDao;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.OrderItemEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by mvolkov on 13.06.2016.
 */
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItemEntity> implements OrderItemDao {

    public OrderItemDaoImpl(EntityManager entityManager)
    {
        super(entityManager);
    }

    public List<OrderItemEntity> getItemsForOrder(String orderId)
    {
        Query query = entityManager.createQuery(
                "select object(o) from OrderItemEntity o where o.order.uid = :orderId"
        );
        query.setParameter("orderId", orderId);
        List<OrderItemEntity> orderItemEntities = null;
        try
        {
            orderItemEntities = (List<OrderItemEntity>) query.getResultList();
        }
        catch (Exception e)
        {
            orderItemEntities = null;
        }
        return orderItemEntities;
    }




}
