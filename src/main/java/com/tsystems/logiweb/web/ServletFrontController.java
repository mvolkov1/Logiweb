package com.tsystems.logiweb.web;

import com.tsystems.logiweb.dao.TransactionManager;
import com.tsystems.logiweb.dao.entity.*;
import com.tsystems.logiweb.services.impl.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

/**
 * Created by mvolkov on 15.06.2016.
 */
public class ServletFrontController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuffer servletUrl = request.getRequestURL();

        if (servletUrl != null) {
            URL url = new URL(servletUrl.toString());
            String path = url.getPath();
            if (path.equals("/Logiweb/") || path.equals("/Logiweb")) {
                String login = request.getParameter("login");
                String password = request.getParameter("pass");
                if (login != null && password != null) {
                    UserEntity userEntity = new UserService().findUserByLogin(login);
                    if (userEntity != null && userEntity.getPassword().equals(password)) {
                        if (userEntity.getRole().equals("manager")) {
                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/pages/managerView.jsp");
                            dispatcher.forward(request, response);
                        } else if (userEntity.getRole().equals("driver")) {
                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/pages/driverView.jsp");
                            dispatcher.forward(request, response);
                        }
                    } else {
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                        dispatcher.forward(request, response);
                    }
                } else {
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                    dispatcher.forward(request, response);
                }
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuffer servletUrl = request.getRequestURL();


        if (servletUrl != null) {
            URL url = new URL(servletUrl.toString());
            String path = url.getPath();
            if (path.equals("/Logiweb") || path.equals("/Logiweb/")) {
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else if (path.equals("/Logiweb/vehicles")) {
                handleVehiclesPage(request, response);
            } else if (path.equals("/Logiweb/drivers")) {
                handleDriversPage(request, response);
            } else if (path.equals("/Logiweb/orders")) {
                handleOrdersPage(request, response);
            } else if (path.equals("/Logiweb/order")) {
                String orderId = request.getParameter("orderId");
                List<OrderItemEntity> items = new OrderItemService().getListOfItems(orderId);
                request.setAttribute("list", items);
                request.setAttribute("orderId", orderId);
                request.getRequestDispatcher("/WEB-INF/pages/order.jsp").forward(request, response);
            } else if (path.equals("/Logiweb/newOrder")) {
                String init = request.getParameter("init");
                if (init != null && init.equals("true")) {
//                    OrderEntity orderEntity = new OrderEntity();
//                    new OrderService().addOrder();
                    request.getRequestDispatcher("/WEB-INF/pages/newOrder.jsp").forward(request, response);
                }
            } else {
                handleError(request, response, path);
            }
        }

    }

    public void handleError(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {


        request.setAttribute("path", path);
        request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);

    }

    private void handleOrdersPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderService orderService = new OrderService();
        OrderItemService orderItemService = new OrderItemService();

        String addOrder = request.getParameter("addOrder");
        String uid = request.getParameter("orderUid");

        if (addOrder != null && addOrder.equals("true"))
        {
            List<OrderItemEntity> items = null;
            if (uid != null)
            {
                String city = request.getParameter("newCity");

                OrderEntity orderEntity = orderService.getOrderByUid(uid);
                int nItems = 0;
                if (orderEntity != null)
                {
                    nItems = orderEntity.getNumberOfItems();
                }
                else
                {
                    orderEntity = new OrderEntity();
                    orderEntity.setNumberOfItems(0);
                    orderEntity.setUid(uid);
                    orderEntity.setIsCompleted((short)0);
                    orderService.save(orderEntity);
                   // orderService.refresh(orderEntity);
                }
                nItems++;
                orderEntity.setNumberOfItems(nItems);
                OrderItemEntity item = new OrderItemEntity();
                item.setCity(new CityService().getCityByName(city));
                item.setOrder(orderEntity);
                item.setItemNumber(nItems);
                orderEntity.getOrderItems().add(item);
                orderItemService.addItem(orderEntity, nItems, new CityService().getCityByName(city));
                orderService.updateOrder(orderEntity, uid, Integer.toString(nItems), "0");
                items = orderItemService.getListOfItems(uid);

            }


            List<CityEntity> cities = new CityService().getListOfCities();
            request.setAttribute("items", items);
            request.setAttribute("cities", cities);
            request.setAttribute("uid", uid);
            request.getRequestDispatcher("/WEB-INF/pages/newOrder.jsp").forward(request, response);
        }
        else {
            List<OrderEntity> orders = new OrderService().getListOfOrders();
            request.setAttribute("list", orders);
            request.getRequestDispatcher("/WEB-INF/pages/orders.jsp").forward(request, response);
        }

    }

    private void handleDriversPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DriverService driverService = new DriverService();

        String deleteDriver = request.getParameter("deleteDriver");
        String newDriver = request.getParameter("newDriver");
        String editDriver = request.getParameter("editDriver");
        String saveDriver = request.getParameter("saveDriver");
        String uid = request.getParameter("uid");

        boolean isDeleteDriver = (deleteDriver != null) && deleteDriver.equals("true");
        boolean IsNewDriver = (newDriver != null) && newDriver.equals("true");
        boolean isEditDriver = (editDriver != null) && editDriver.equals("true");
        boolean isSaveDriver = (saveDriver != null) && saveDriver.equals("true");
        boolean hasUid = (uid != null);

        if (isEditDriver || IsNewDriver) {
            if (isEditDriver && hasUid) {
                DriverEntity driverEntity = driverService.getDriverByUid(uid);
                request.setAttribute("uid", uid);
                request.setAttribute("driverCity", driverEntity.getCity().getCity());
                request.setAttribute("monthHours", driverEntity.getMonthHours());
                request.setAttribute("status", driverEntity.getStatus());
            } else {
                request.setAttribute("monthHours", "0");
                request.setAttribute("status", "free");
            }


            List<CityEntity> cities = new CityService().getListOfCities();
            request.setAttribute("cities", cities);
            request.getRequestDispatcher("/WEB-INF/pages/editDriver.jsp").forward(request, response);

        } else {

            if (isDeleteDriver && hasUid)
                driverService.deleteDriver(uid);

            if (isSaveDriver && hasUid) {

                String monthHours = request.getParameter("monthHours");
                String status = request.getParameter("status1");
                String city = request.getParameter("city");

                DriverEntity driverEntity = driverService.getDriverByUid(uid);
                if (driverEntity != null) {
                    OrderEntity order = driverEntity.getOrder();
                    CityEntity cityEntity = new CityService().getCityByName(city);

                    driverEntity.setCity(cityEntity);
                    driverEntity.setStatus(status);
                    driverEntity.setMonthHours(Integer.parseInt(monthHours));


                    driverService.updateDriver(driverEntity, uid, monthHours, status, cityEntity, order);
                } else {
//                    driverService.addDriver(uid, Float.parseFloat(capacity), Integer.parseInt(numberOfDrivers),
//                            Short.parseShort(isAvailable),city);
                }
            }

            List<DriverEntity> drivers = driverService.getListOfDrivers();
            request.setAttribute("list", drivers);
            request.getRequestDispatcher("/WEB-INF/pages/drivers.jsp").forward(request, response);
        }


        List<DriverEntity> drivers = new DriverService().getListOfDrivers();
        request.setAttribute("list", drivers);
        request.getRequestDispatcher("/WEB-INF/pages/drivers.jsp").forward(request, response);
    }


    private void handleVehiclesPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        VehicleService vehicleService = new VehicleService();

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


        if (isEditVehicle || IsNewVehicle) {
            if (isEditVehicle && hasVin) {
                VehicleEntity vehicleEntity = vehicleService.getVehicleByVin(vin);
                request.setAttribute("vin", vin);
                request.setAttribute("vehicleCity", vehicleEntity.getCity().getCity());
                request.setAttribute("capacity", vehicleEntity.getCapacity());
                request.setAttribute("numberOfDrivers", vehicleEntity.getNumberOfDrivers());
                short isAvailable = vehicleEntity.getIsAvailable();
                request.setAttribute("isAvailable", (isAvailable == 0) ? "no" : "yes");
            }

            List<CityEntity> cities = new CityService().getListOfCities();
            request.setAttribute("cities", cities);
            request.getRequestDispatcher("/WEB-INF/pages/editVehicle.jsp").forward(request, response);

        } else {

            if (isDeleteVehicle && hasVin)
                vehicleService.deleteVehicle(vin);

            if (isSaveVehicle && hasVin) {

                String capacity = request.getParameter("capacity");
                String numberOfDrivers = request.getParameter("numberOfDrivers");
                String city = request.getParameter("city");
                String isAvailable = request.getParameter("isAvailable1");
                isAvailable = isAvailable.equals("no") ? "0" : "1";

                VehicleEntity vehicleEntity = vehicleService.getVehicleByVin(vin);
                if (vehicleEntity != null) {
                    OrderEntity order = vehicleEntity.getOrder();
                    CityEntity cityEntity = new CityService().getCityByName(city);

                    vehicleEntity.setCity(cityEntity);
                    vehicleEntity.setCapacity(new BigDecimal(capacity));
                    vehicleEntity.setNumberOfDrivers(Integer.parseInt(numberOfDrivers));
                    vehicleEntity.setIsAvailable(Short.parseShort(isAvailable));

                    vehicleService.updateVehicle(vehicleEntity, vin, capacity, numberOfDrivers,
                            isAvailable, cityEntity, order);
                } else {
                    vehicleService.addVehicle(vin, Float.parseFloat(capacity), Integer.parseInt(numberOfDrivers),
                            Short.parseShort(isAvailable), city);
                }
            }

            List<VehicleEntity> vehicles = vehicleService.getListOfVehicles();
            request.setAttribute("list", vehicles);
            request.getRequestDispatcher("/WEB-INF/pages/vehicles.jsp").forward(request, response);
        }
    }


}
