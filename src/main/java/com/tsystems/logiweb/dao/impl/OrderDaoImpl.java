package com.tsystems.logiweb.dao.impl;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.tsystems.logiweb.dao.api.OrderDao;
import com.tsystems.logiweb.dao.entity.OrderEntity;

import javax.persistence.Query;


/**
 * Created by mvolkov on 13.06.2016.
 */
public class OrderDaoImpl extends BaseDaoImpl<OrderEntity> implements OrderDao{

//    public void save(OrderEntity order) {
//        super.save(order);
//    }
//
//    public List<OrderEntity> getAllOrders() {
//        Query query = em.createQuery("select object(o) from OrderEntity o");
//        return (List<OrderEntity>) query.getResultList();
//    }
    public OrderEntity findByOrderId(String orderId)
    {
        String queryString = "select object(o) from OrderEntity o where o.uid='" + orderId + "'";
        Query query = em.createQuery(queryString);
        return (OrderEntity) query.getSingleResult();

    }
}
