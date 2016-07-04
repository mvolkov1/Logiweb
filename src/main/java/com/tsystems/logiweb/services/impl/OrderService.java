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

    public static void createOrder(String uid) {
        try {
            TransactionManager.beginTransaction();
            OrderEntity orderEntity = new OrderDaoImpl(TransactionManager.getEntityManager()).findByUid(uid);
            if (orderEntity == null) {
                orderEntity = new OrderEntity();
                orderEntity.setUid(uid);
            }
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }


    public void addItem(OrderEntity orderEntity, int itemNumber, CityEntity cityEntity) {
        OrderItemDao orderItemDao = new OrderItemDaoImpl(TransactionManager.getEntityManager());
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setOrder(orderEntity);
        orderItemEntity.setItemNumber(itemNumber);
        orderItemEntity.setCity(cityEntity);
        orderItemDao.save(orderItemEntity);
    }

    public static String handleEditOrder(HttpServletRequest request) {
        String address = null;

        OrderDao orderDao = new OrderDaoImpl(TransactionManager.getEntityManager());
        CityDao cityDao = new CityDaoImpl(TransactionManager.getEntityManager());
        OrderItemDao orderItemDao = new OrderItemDaoImpl(TransactionManager.getEntityManager());

        String uid = request.getParameter("uid");
        String saveOrder = request.getParameter("saveOrder");
        String oldUid = request.getParameter("oldUid");
        String saveOrderItem = request.getParameter("saveOrderItem");
        String city = request.getParameter("city");

        if (uid != null && !uid.isEmpty()) {
            try {
                TransactionManager.beginTransaction();

                OrderEntity orderEntity = orderDao.findByUid(oldUid == null ? uid : oldUid);
                if (orderEntity == null) {
                    orderEntity = new OrderEntity();
                    orderEntity.setUid(uid);
                    orderDao.save(orderEntity);
                }

                if (city != null && !city.isEmpty()) {
                    OrderItemEntity orderItemEntity = new OrderItemEntity();
                    CityEntity cityEntity = null;
                    cityEntity = cityDao.findByName(city);
                    if (cityEntity != null) {
                        orderItemEntity.setCity(cityDao.findByName(city));
                        orderItemEntity.setItemNumber(orderEntity.getOrderItems().size() + 1);
                        orderItemEntity.setOrder(orderEntity);
                        orderEntity.getOrderItems().add(orderItemEntity);
                    }
                }
                TransactionManager.commit();

                List<OrderItemEntity> items = (List<OrderItemEntity>) orderEntity.getOrderItems();
                request.setAttribute("items", items);
            } catch (Exception e) {
                TransactionManager.rollback();
            }
            request.setAttribute("uid", uid);
        }
        List<CityEntity> cities = cityDao.getAllEntities(CityEntity.class);
        request.setAttribute("cities", cities);

        return "/WEB-INF/jsp/editOrder.jsp";
    }

    public void showOrder(HttpServletRequest request) {

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


        List<OrderItemEntity> items = null;
        List<VehicleEntity> vehicles = null;
        List<CargoEntity> cargos = null;
        VehicleEntity vehicle = null;
        List<DriverEntity> drivers = null;
        List<DriverEntity> assignedDrivers = null;
        List<CityEntity> cities = null;
        Set<CityEntity> assignedCities = null;

        String orderId = request.getParameter("orderId");
        if (path.equals("/Logiweb/order") && orderId != null) {
            editOrder = "true";
            isEmpty = false;
            saveId = null;
            saveOrderItem = null;
            saveCargo = null;
            saveVehicle = null;
            saveDriver = null;

            uid = orderId;
            request.setAttribute("orderCompletlyCreated", "true");
        }

        if (editOrder != null && editOrder.equals("true")) {

            if (!isEmpty) {
                try {
                    TransactionManager.beginTransaction();

                    cities = cityDao.getAllEntities(CityEntity.class);

                    OrderEntity orderEntity = orderDao.findByUid(uid);
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
                            orderEntity.setNumberOfItems(nItems);
                            orderItemDao.save(item);
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
                    }

                    if (saveVehicle != null && saveVehicle.equals("true")) {
                        if (vin != null) {
                            vehicle = vehicleDao.findByVin(vin);
                            if (vehicle != null) {
                                vehicle.setOrder(orderEntity);
                                orderEntity.setVehicle(vehicle);
                                vehicleDao.merge(vehicle); //setOrderForVehicle(vehicle, orderEntity);
                            }
                        }
                    }

                    if (saveDriver != null && saveDriver.equals("true")) {
                        DriverEntity driver = driverDao.findByUid(driverUid);
                        if (driver != null) {
                            driver.setOrder(orderEntity);
                            orderEntity.getDrivers().add(driver);
                            driverDao.setOrderForDriver(driver, orderEntity);
                        }
                    }

                    items = (List<OrderItemEntity>) orderEntity.getOrderItems();
                    cargos = (List<CargoEntity>) orderEntity.getCargos();
                    vehicle = orderEntity.getVehicle();
                    drivers = driverDao.getFreeDrivers();
                    assignedDrivers = (List<DriverEntity>) orderEntity.getDrivers();


                    assignedCities = new HashSet<CityEntity>();
                    if (items != null) {
                        for (OrderItemEntity item : items) {
                            assignedCities.add(item.getCity());
                        }
                    } else {
                        assignedCities = null;
                    }

                    TransactionManager.commit();

                    if (cargos != null && items != null && cargos.size() > 0 && items.size() > 0) {
                        vehicles = vehicleDao.getListOfVehiclesForOrder(orderEntity);
                    }


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
                if (assignedDrivers != null && vehicle != null &&
                        assignedDrivers.size() == vehicle.getNumberOfDrivers()) {
                    request.setAttribute("orderCompletlyCreated", "true");
                    request.setAttribute("editOrder", "false");
                }
            }
            request.getRequestDispatcher("/WEB-INF/jsp/editOrder.jsp").forward(request, response);
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
        } catch (Exception e) {
            TransactionManager.rollback();
            orders = null;
        }
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/jsp/orders.jsp").forward(request, response);
    }

    public static List<OrderEntity> getListOfOrders() {
        List<OrderEntity> orders = null;
        try {
            TransactionManager.beginTransaction();
            orders = new OrderDaoImpl(TransactionManager.getEntityManager()).getAllEntities(OrderEntity.class);
            TransactionManager.commit();
        } catch (Exception e) {
            orders = null;
        }
        return orders;
    }
}
