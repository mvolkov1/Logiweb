package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.services.TransactionManager;
import com.tsystems.logiweb.dao.api.CargoDao;
import com.tsystems.logiweb.dao.entity.CargoEntity;
import com.tsystems.logiweb.dao.impl.CargoDaoImpl;

import java.util.List;

/**
 * Created by mvolkov on 22.06.2016.
 */
public class CargoService {


    private CargoDao cargoDao = new CargoDaoImpl(TransactionManager.getEntityManager());

}
