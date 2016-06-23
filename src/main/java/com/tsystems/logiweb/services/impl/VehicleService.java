package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.services.TransactionManager;
import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.api.VehicleDao;
import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.VehicleEntity;
import com.tsystems.logiweb.dao.impl.CityDaoImpl;
import com.tsystems.logiweb.dao.impl.VehicleDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by mvolkov on 27.05.2016.
 */
public class VehicleService {

    private VehicleDao vehicleDao = new VehicleDaoImpl(TransactionManager.getEntityManager());


    public void addVehicle(String vin, float capacity, int numberOfDrivers, short isAvailable, String city)
    {
        try{
            TransactionManager.beginTransaction();

            CityDao cityDao = new CityDaoImpl(TransactionManager.getEntityManager());
            VehicleEntity vehicleEntity = new VehicleEntity();
            vehicleEntity.setVin(vin);
            vehicleEntity.setCapacity(new BigDecimal(capacity));
            vehicleEntity.setNumberOfDrivers(numberOfDrivers);
            vehicleEntity.setCity(cityDao.findByName(city));
            vehicleEntity.setIsAvailable(isAvailable);

            vehicleDao.save(vehicleEntity);
            TransactionManager.commit();
        }
        catch (Exception e) {
            TransactionManager.rollback();
        }
    }



    public void handleVehiclesPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String deleteVehicle = request.getParameter("deleteVehicle");
        String newVehicle = request.getParameter("newVehicle");
        String editVehicle = request.getParameter("editVehicle");
        String saveVehicle = request.getParameter("saveVehicle");
        String vin = request.getParameter("vin");


        boolean isDeleteVehicle = (deleteVehicle != null) && deleteVehicle.equals("true");
        boolean IsNewVehicle = (newVehicle != null) && newVehicle.equals("true");
        boolean isEditVehicle = (editVehicle != null) && editVehicle.equals("true");
        boolean isSaveVehicle = (saveVehicle != null) && saveVehicle.equals("true");
        boolean hasVin = (vin != null);

        CityDao cityDao = new CityDaoImpl(TransactionManager.getEntityManager());
        List<CityEntity> cities = null;
        VehicleEntity vehicleEntity = null;
        List<VehicleEntity> vehicles = null;


        if (isEditVehicle || IsNewVehicle) {
            if (isEditVehicle && hasVin) {
                try {
                    TransactionManager.beginTransaction();
                    vehicleEntity = vehicleDao.findByVin(vin);
                    TransactionManager.commit();
                }
                catch (Exception e)
                {
                    vehicleEntity = null;
                    TransactionManager.rollback();
                }
                if (vehicleEntity != null)
                {
                    request.setAttribute("vin", vin);
                    request.setAttribute("vehicleCity", vehicleEntity.getCity().getCity());
                    request.setAttribute("capacity", vehicleEntity.getCapacity());
                    request.setAttribute("numberOfDrivers", vehicleEntity.getNumberOfDrivers());
                    short isAvailable = vehicleEntity.getIsAvailable();
                    request.setAttribute("isAvailable", (isAvailable == 0) ? "no" : "yes");
                }

            }
            cities = cityDao.getAllEntities(CityEntity.class);
            request.setAttribute("cities", cities);
            request.getRequestDispatcher("/WEB-INF/jsp/editVehicle.jsp").forward(request, response);

        } else {

            try {
                TransactionManager.beginTransaction();


                if (isDeleteVehicle && hasVin)
                    vehicleDao.deleteByVin(vin);

                if (isSaveVehicle && hasVin) {

                    String capacity = request.getParameter("capacity");
                    String numberOfDrivers = request.getParameter("numberOfDrivers");
                    String city = request.getParameter("city");
                    String isAvailable = request.getParameter("isAvailable1");
                    isAvailable = isAvailable.equals("no") ? "0" : "1";

                    vehicleEntity = vehicleDao.findByVin(vin);

                    CityEntity cityEntity = cityDao.findByName(city);
                    vehicleEntity.setCity(cityEntity);
                    vehicleEntity.setCapacity(new BigDecimal(capacity));
                    vehicleEntity.setNumberOfDrivers(Integer.parseInt(numberOfDrivers));
                    vehicleEntity.setIsAvailable(Short.parseShort(isAvailable));

                    if (vehicleEntity != null) {
                        OrderEntity order = vehicleEntity.getOrder();
                        vehicleDao.updateVehicle(vehicleEntity, vin, capacity, numberOfDrivers,
                                isAvailable, cityEntity, order);
                    } else {
                        vehicleEntity = new VehicleEntity();
                        vehicleEntity.setVin(vin);
                        vehicleEntity.setCity(cityEntity);
                        vehicleEntity.setCapacity(new BigDecimal(capacity));
                        vehicleEntity.setNumberOfDrivers(Integer.parseInt(numberOfDrivers));
                        vehicleEntity.setIsAvailable(Short.parseShort(isAvailable));
                        vehicleDao.save(vehicleEntity);
                    }
                }

                vehicles = vehicleDao.getAllEntities(VehicleEntity.class);

                TransactionManager.commit();
            }
            catch (Exception e)
            {
                TransactionManager.rollback();
            }
            request.setAttribute("list", vehicles);
            request.getRequestDispatcher("/WEB-INF/jsp/vehicles.jsp").forward(request, response);
        }
    }

}
