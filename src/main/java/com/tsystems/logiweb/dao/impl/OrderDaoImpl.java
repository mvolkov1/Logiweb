package com.tsystems.logiweb.dao.impl;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.tsystems.logiweb.dao.api.OrderDao;
import com.tsystems.logiweb.dao.entity.OrderEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;


/**
 * Created by mvolkov on 13.06.2016.
 */
public class OrderDaoImpl extends BaseDaoImpl<OrderEntity> implements OrderDao{

    public OrderDaoImpl(EntityManager entityManager)
    {
        super(entityManager);
    }

    public OrderEntity findByUid(String orderId)
    {
        Query query = entityManager.createQuery("select object(o) from OrderEntity o where o.uid=:id");
        query.setParameter("id", orderId);

        OrderEntity orderEntity = null;
        try
        {
            orderEntity = (OrderEntity) query.getSingleResult();
        }
        catch(Exception e)
        {
            orderEntity = null;
        }

        return orderEntity;
    }




    public void updateOrder(OrderEntity orderEntity, String uid, String numberOfItems, String isCompleted)
    {
        Query query = entityManager.createQuery("update OrderEntity o"
                + " set o.uid = :uid, "
                + " o.numberOfItems= :numberOfItems, "
                + " o.isCompleted = :isCompleted "
                + " where o.id = :id");
        query.setParameter("id", orderEntity.getId());
        query.setParameter("uid", uid);
        query.setParameter("numberOfItems", Integer.parseInt(numberOfItems));
        query.setParameter("isCompleted", Short.parseShort(isCompleted));

        int updateCount = 0;
        try
        {
            updateCount = query.executeUpdate();
        }
        catch (Exception e)
        {

        }
    }
}
