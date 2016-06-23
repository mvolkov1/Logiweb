package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.services.TransactionManager;
import com.tsystems.logiweb.dao.api.CityDao;
import com.tsystems.logiweb.dao.api.DriverDao;
import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.DriverEntity;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.UserEntity;
import com.tsystems.logiweb.dao.impl.CityDaoImpl;
import com.tsystems.logiweb.dao.impl.DriverDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by mvolkov on 16.06.2016.
 */
public class DriverService {

    private DriverDao driverDao = new DriverDaoImpl(TransactionManager.getEntityManager());


    public void addDriver(String login, String password, String firstName, String lastName,
                          String uid, int monthHours, String status, String city) {

        try{
            TransactionManager.beginTransaction();
            DriverEntity driverEntity = new DriverEntity();
            driverEntity.setUid(uid);
            driverEntity.setMonthHours(monthHours);
            driverEntity.setStatus(status);
            driverEntity.setCity(new CityDaoImpl(TransactionManager.getEntityManager()).findByName(city));

            UserEntity userEntity = new UserEntity();
            userEntity.setLogin(login);
            userEntity.setPassword(password);
            userEntity.setRole("driver");
            userEntity.setFirstName(firstName);
            userEntity.setLastName(lastName);

            driverEntity.setUser(userEntity);

            driverDao.save(driverEntity);
            TransactionManager.commit();

        }
        catch (Exception e)
        {
            TransactionManager.rollback();
        }


    }


    public void handleDriversPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DriverService driverService = new DriverService();

        String deleteDriver = request.getParameter("deleteDriver");
        String newDriver = request.getParameter("newDriver");
        String editDriver = request.getParameter("editDriver");
        String saveDriver = request.getParameter("saveDriver");
        String uid = request.getParameter("uid");

        CityDao cityDao = new CityDaoImpl(TransactionManager.getEntityManager());

        boolean isDeleteDriver = (deleteDriver != null) && deleteDriver.equals("true");
        boolean IsNewDriver = (newDriver != null) && newDriver.equals("true");
        boolean isEditDriver = (editDriver != null) && editDriver.equals("true");
        boolean isSaveDriver = (saveDriver != null) && saveDriver.equals("true");
        boolean hasUid = (uid != null);

        if (isEditDriver || IsNewDriver) {
            if (isEditDriver && hasUid) {
                try {
                    TransactionManager.beginTransaction();
                    DriverEntity driverEntity = driverDao.findByUid(uid);
                    request.setAttribute("uid", uid);
                    request.setAttribute("driverCity", driverEntity.getCity().getCity());
                    request.setAttribute("monthHours", driverEntity.getMonthHours());
                    request.setAttribute("status", driverEntity.getStatus());
                    request.setAttribute("firstName", driverEntity.getUser().getFirstName());
                    request.setAttribute("lastName", driverEntity.getUser().getLastName());
                    TransactionManager.commit();
                } catch (Exception e) {
                    TransactionManager.rollback();
                }
            } else {
                request.setAttribute("monthHours", "0");
                request.setAttribute("status", "free");
            }

            List<CityEntity> cities = cityDao.getAllEntities(CityEntity.class);
            request.setAttribute("cities", cities);
            request.getRequestDispatcher("/WEB-INF/jsp/editDriver.jsp").forward(request, response);

        } else {

            List<DriverEntity> drivers = null;

            try {
                TransactionManager.beginTransaction();

                if (isDeleteDriver && hasUid)
                    driverDao.deleteByUid(uid);

                if (isSaveDriver && hasUid) {

                    String monthHours = request.getParameter("monthHours");
                    String status = request.getParameter("status1");
                    String city = request.getParameter("city");

                    DriverEntity driverEntity = driverDao.findByUid(uid);
                    if (driverEntity != null) {
                        OrderEntity order = driverEntity.getOrder();
                        CityEntity cityEntity = cityDao.findByName(city);

                        driverEntity.setCity(cityEntity);
                        driverEntity.setStatus(status);
                        driverEntity.setMonthHours(Integer.parseInt(monthHours));

                        driverDao.updateDriver(driverEntity, uid, monthHours, status, cityEntity, order);
                    } else {
                        //   driverService.addDriver(uid, Float.parseFloat(capacity), Integer.parseInt(numberOfDrivers),
                        //   Short.parseShort(isAvailable),city);
                    }
                }

                drivers = driverDao.getAllEntities(DriverEntity.class);
                TransactionManager.commit();
            } catch (Exception e) {
                TransactionManager.rollback();
            }
            request.setAttribute("list", drivers);
            request.getRequestDispatcher("/WEB-INF/jsp/drivers.jsp").forward(request, response);
        }
    }
}
