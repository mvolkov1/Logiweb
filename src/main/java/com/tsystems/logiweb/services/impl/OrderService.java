package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.api.*;
import com.tsystems.logiweb.dao.entity.*;
import com.tsystems.logiweb.dao.impl.*;
import com.tsystems.logiweb.services.TransactionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mvolkov on 17.06.2016.
 */
public class OrderService {

    private OrderDao orderDao = new OrderDaoImpl(TransactionManager.getEntityManager());
    CargoDao cargoDao = new CargoDaoImpl(TransactionManager.getEntityManager());
    VehicleDao vehicleDao = new VehicleDaoImpl(TransactionManager.getEntityManager());
    DriverDao driverDao = new DriverDaoImpl(TransactionManager.getEntityManager());
    CityDao cityDao = new CityDaoImpl(TransactionManager.getEntityManager());
    OrderItemDao orderItemDao = new OrderItemDaoImpl(TransactionManager.getEntityManager());

    public List<OrderEntity> getListOfOrders() {
        return orderDao.getAllEntities(OrderEntity.class);
    }

    public void addItem(OrderEntity orderEntity, int itemNumber, CityEntity cityEntity) {
        OrderItemDao orderItemDao = new OrderItemDaoImpl(TransactionManager.getEntityManager());
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setOrder(orderEntity);
        orderItemEntity.setItemNumber(itemNumber);
        orderItemEntity.setCity(cityEntity);
        orderItemDao.save(orderItemEntity);
    }

    public void handleNewOrder(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {

        String editOrder = request.getParameter("editOrder");
        String uid = request.getParameter("uid");
        String saveId = request.getParameter("saveId");
        String emptyOrder = request.getParameter("emptyOrder");
        boolean isEmpty = emptyOrder != null && emptyOrder.equals("true");
        String saveOrderItem = request.getParameter("saveOrderItem");
        String saveCargo = request.getParameter("saveCargo");
        String saveVehicle = request.getParameter("saveVehicle");
        String vin = request.getParameter("vin");
        String saveDriver = request.getParameter("saveDriver");
        String driverUid = request.getParameter("driverUid");

        String orderId = request.getParameter("orderId");
        if (path.equals("/Logiweb/order") && orderId != null)
        {
            editOrder = "true";
            uid = orderId;
            request.setAttribute("orderCompletlyCreated", "true");
        }


        List<OrderItemEntity> items = null;
        List<VehicleEntity> vehicles = null;
        List<CargoEntity> cargos = null;
        VehicleEntity vehicle = null;
        List<DriverEntity> drivers = null;
        List<DriverEntity> assignedDrivers = null;
        List<CityEntity> cities = null;
        Set<CityEntity> assignedCities = null;

        if (editOrder != null && editOrder.equals("true")) {

            if (!isEmpty) {
                try {
                    TransactionManager.beginTransaction();

                    cities = cityDao.getAllEntities(CityEntity.class);

                    OrderEntity orderEntity = orderDao.findByOrderId(uid);
                    if (saveId != null && saveId.equals("true")) {
                        if (orderEntity == null) {
                            orderEntity = new OrderEntity();
                            orderEntity.setNumberOfItems(0);
                            orderEntity.setUid(uid);
                            orderEntity.setIsCompleted((short) 0);
                            orderDao.save(orderEntity);
                        }
                    }

                    if (saveOrderItem != null && saveOrderItem.equals("true")) {
                        String newCity = request.getParameter("newCity");
                        CityEntity cityEntity = cityDao.findByName(newCity);
                        if (cityEntity != null) {
                            OrderItemEntity item = new OrderItemEntity();
                            item.setCity(cityDao.findByName(newCity));
                            item.setOrder(orderEntity);
                            int nItems = orderEntity.getNumberOfItems();
                            nItems++;
                            item.setItemNumber(nItems);
                            orderEntity.getOrderItems().add(item);
                            orderEntity.setNumberOfItems(nItems);
                            this.addItem(orderEntity, nItems, cityDao.findByName(newCity));
                            orderDao.updateOrder(orderEntity, uid, Integer.toString(nItems), "0");
                        }
                    }

                    if (saveCargo != null && saveCargo.equals("true")) {
                        String cargoId = request.getParameter("cargoId");
                        String mass = request.getParameter("cargoMass");
                        String title = request.getParameter("cargoTitle");
                        String city1 = request.getParameter("cargoCity1");
                        String city2 = request.getParameter("cargoCity2");
                        CargoEntity cargo = new CargoEntity();
                        cargo.setUid(cargoId);
                        cargo.setTitle(title);
                        cargo.setMass(new BigDecimal(mass));
                        cargo.setStartCity(cityDao.findByName(city1));
                        cargo.setEndCity(cityDao.findByName(city2));
                        cargo.setOrder(orderEntity);
                        cargo.setStatus("Prepared");
                        cargoDao.save(cargo);
                        orderEntity.getCargos().add(cargo);
                    }

                    if (saveVehicle != null && saveVehicle.equals("true")) {
                        if (vin != null) {
                            VehicleService vehicleService = new VehicleService();
                            vehicle = vehicleDao.findByVin(vin);
                            if (vehicle != null) {
                                vehicle.setOrder(orderEntity);
                                orderEntity.setVehicle(vehicle);
                                vehicleDao.setOrderForVehicle(vehicle, orderEntity);
                            }
                        }
                    }

                    if (saveDriver != null && saveDriver.equals("true")) {
                        DriverEntity driver = driverDao.findByUid(driverUid);
                        if (driver != null) {
                            orderEntity.getDrivers().add(driver);
                            driverDao.updateDriver(driver, driverUid, Integer.toString(driver.getMonthHours()),
                                    driver.getStatus(), driver.getCity(), orderEntity);
                        }
                    }

                    items = (List<OrderItemEntity>) orderEntity.getOrderItems();
                    cargos = (List<CargoEntity>) orderEntity.getCargos();
                    vehicle = orderEntity.getVehicle();
                    drivers = driverDao.getAllEntities(DriverEntity.class);
                    assignedDrivers = (List<DriverEntity>) orderEntity.getDrivers();
                    vehicles = vehicleDao.getAllEntities(VehicleEntity.class);

                    assignedCities = new HashSet<CityEntity>();
                    if (items != null) {
                        for (OrderItemEntity item : items) {
                            assignedCities.add(item.getCity());
                        }
                    }

                    TransactionManager.commit();
                } catch (Exception e) {
                    TransactionManager.rollback();
                }


                request.setAttribute("items", items);
                if (items != null && items.size() > 0) {
                    request.setAttribute("hasItems", "true");
                }

                request.setAttribute("cargos", cargos);
                if (cargos != null && cargos.size() > 0) {
                    request.setAttribute("hasCargos", "true");
                }
                request.setAttribute("vehicle", vehicle);
                request.setAttribute("vehicles", vehicles);
                request.setAttribute("drivers", drivers);
                request.setAttribute("assignedDrivers", assignedDrivers);
                request.setAttribute("cities", cities);
                request.setAttribute("assignedCities", assignedCities);
                request.setAttribute("uid", uid);
                request.setAttribute("vin", vin);
                if (assignedDrivers != null && assignedDrivers.size() > 2) {
                    request.setAttribute("orderCompletlyCreated", "true");
                }
            }
            request.getRequestDispatcher("/WEB-INF/jsp/newOrder.jsp").forward(request, response);
        } else {
            List<OrderEntity> orders = new OrderService().getListOfOrders();
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/WEB-INF/jsp/orders.jsp").forward(request, response);
        }
    }

    public void handleOrdersPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<OrderEntity> orders = null;
        try {
            TransactionManager.beginTransaction();
            orders = orderDao.getAllEntities(OrderEntity.class);
            TransactionManager.commit();
        }catch (Exception e)
        {
            TransactionManager.rollback();
            orders=null;
        }
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/jsp/orders.jsp").forward(request, response);
    }
}
