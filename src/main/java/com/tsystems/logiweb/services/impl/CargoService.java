package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.TransactionManager;
import com.tsystems.logiweb.dao.api.CargoDao;
import com.tsystems.logiweb.dao.entity.CargoEntity;
import com.tsystems.logiweb.dao.impl.CargoDaoImpl;

import java.util.List;

/**
 * Created by mvolkov on 22.06.2016.
 */
public class CargoService {

    private TransactionManager transactionManager = TransactionManager.getInstance();
    private CargoDao cargoDao = new CargoDaoImpl(transactionManager.getEntityManager());

    public List<CargoEntity> getListOfItems(String orderId){
        return cargoDao.getCargosForOrder(orderId);
    }

    public void saveCargo(CargoEntity cargoEntity)
    {
        try {
            transactionManager.beginTransaction();
            try{

                cargoDao.save(cargoEntity);

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
