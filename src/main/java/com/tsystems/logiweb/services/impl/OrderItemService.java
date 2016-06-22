package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.TransactionManager;
import com.tsystems.logiweb.dao.api.OrderItemDao;
import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.OrderItemEntity;
import com.tsystems.logiweb.dao.impl.OrderItemDaoImpl;

import java.util.List;

/**
 * Created by mvolkov on 17.06.2016.
 */
public class OrderItemService {

    private TransactionManager transactionManager = TransactionManager.getInstance();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl(transactionManager.getEntityManager());

    public List<OrderItemEntity> getListOfItems(String orderId){
        return orderItemDao.getItemsForOrder(orderId);
    }

    public void save(OrderItemEntity item)
    {
        orderItemDao.save(item);
    }

    public void saveCargo(OrderItemEntity orderItemEntity)
    {
        try {
            transactionManager.beginTransaction();
            try{

                orderItemDao.save(orderItemEntity);

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

    public void addItem(OrderEntity orderEntity, int itemNumber, CityEntity cityEntity)
    {
        try {
            transactionManager.beginTransaction();
            try{

                OrderItemDao orderItemDao = new OrderItemDaoImpl(transactionManager.getEntityManager());
                OrderItemEntity orderItemEntity = new OrderItemEntity();
                orderItemEntity.setOrder(orderEntity);
                orderItemEntity.setItemNumber(itemNumber);
                orderItemEntity.setCity(cityEntity);
                orderItemDao.save(orderItemEntity);

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
