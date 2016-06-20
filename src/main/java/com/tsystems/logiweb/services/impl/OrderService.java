package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.TransactionManager;
import com.tsystems.logiweb.dao.api.OrderDao;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.impl.OrderDaoImpl;

import java.util.List;

/**
 * Created by mvolkov on 17.06.2016.
 */
public class OrderService {

    private TransactionManager transactionManager = TransactionManager.getInstance();
    private OrderDao orderDao = new OrderDaoImpl(transactionManager.getEntityManager());


    public synchronized  List<OrderEntity> getListOfOrders(){
        return orderDao.getAllEntities(OrderEntity.class);
    }

    public void addOrder(String uid, int numberOfItems)
    {
        try {
            transactionManager.beginTransaction();
            try {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setNumberOfItems(numberOfItems);
                orderEntity.setIsCompleted((short)0);
                orderEntity.setUid(uid);
                orderDao.save(orderEntity);
                transactionManager.commitTransaction();
            }
            catch (Exception e)
            {
                transactionManager.rollbackTransaction();
            }
        }
        catch (Exception e)
        {}
    }

    public void save(OrderEntity item)
    {
        try {
            transactionManager.beginTransaction();
            try {
                orderDao.save(item);
                transactionManager.commitTransaction();
            }
            catch (Exception e)
            {
                transactionManager.rollbackTransaction();
            }
        }
        catch (Exception e)
        {}
    }

    public void refresh(OrderEntity orderEntity)
    {
        orderDao.refresh(orderEntity);
    }

    public synchronized OrderEntity getOrderByUid(String uid){
        return orderDao.findByOrderId(uid);
    }

    public synchronized void updateOrder(OrderEntity orderEntity, String uid, String numberOfItems,
                                         String isCompleted)
    {
        try {
            transactionManager.beginTransaction();
            try{
                orderDao.updateOrder(orderEntity, uid, numberOfItems, isCompleted);
                transactionManager.commitTransaction();

            } catch (Exception e) {

                transactionManager.rollbackTransaction();
                //TODO   throw new LogiwebServiceException("", e);
            } finally {
//                transactionManager.close();
            }
        }
        catch (Exception e)
        {
            // TODO throw new TransactionManagerException("",e)
        }
    }
}
