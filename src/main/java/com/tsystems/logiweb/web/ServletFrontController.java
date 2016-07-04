package com.tsystems.logiweb.web;

import com.tsystems.logiweb.dao.entity.CityEntity;
import com.tsystems.logiweb.dao.entity.DriverEntity;
import com.tsystems.logiweb.dao.entity.OrderEntity;
import com.tsystems.logiweb.dao.entity.VehicleEntity;
import com.tsystems.logiweb.services.impl.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            String address = null;
            if (path.equals("/Logiweb/") || path.equals("/Logiweb")) {
                String login = request.getParameter("login");
                String password = request.getParameter("pass");
                if (login != null && password != null) {
                    StringBuilder firstName = new StringBuilder();
                    StringBuilder lastName = new StringBuilder();
                    address = UserService.handleLoginPassword(login, password, firstName, lastName);
                    if (address != null) {
                        request.setAttribute("lastName", lastName.toString());
                        request.setAttribute("firstName", firstName.toString());
                        request.getRequestDispatcher(address).forward(request, response);
                        return;
                    }
                }
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
                return;
            } else if (path.equals("/Logiweb/vehicles")) {
                String deleteVehicle = request.getParameter("deleteVehicle");
                String saveVehicle = request.getParameter("saveVehicle");
                String vin = request.getParameter("vin");
                if (vin != null && !vin.isEmpty()) {
                    if (deleteVehicle != null && deleteVehicle.equals("true")) {
                        VehicleService.deleteVehicle(vin);
                    }
                    if (saveVehicle != null && saveVehicle.equals("true")) {
                        String capacity = request.getParameter("capacity");
                        String numberOfDrivers = request.getParameter("numberOfDrivers");
                        String city = request.getParameter("city");
                        String isAvailable = request.getParameter("isAvailable1");
                        isAvailable = isAvailable.equals("no") ? "0" : "1";
                        VehicleService.saveVehicle(vin, capacity, numberOfDrivers, city, isAvailable);
                    }
                }
                response.sendRedirect("vehicles");
                return;
            } else if (path.equals("/Logiweb/drivers")) {
                String uid = request.getParameter("uid");
                String saveDriver = request.getParameter("saveDriver");
                if (uid != null && !uid.isEmpty()) {
                    if (saveDriver != null && saveDriver.equals("true")) {
                        String oldUid = request.getParameter("oldUid");
                        String monthHours = request.getParameter("monthHours");
                        String city = request.getParameter("city");
                        String status = request.getParameter("status1");
                        DriverService.saveDriver(uid, oldUid, monthHours, status, city);
                    }
                }
                response.sendRedirect("drivers");
                return;
            } else if (path.equals("/Logiweb/editOrder")) {
                String uid = request.getParameter("uid");
                String step = request.getParameter("step");
                if (uid != null && !uid.isEmpty() && step != null) {
                    if (step.equals("uid"))
                    {
                        OrderService.createOrder(uid);
                    }


                }
                response.sendRedirect("editOrder?uid=" + uid);
                return;
//                address = OrderService.handleEditOrder(request);
//            } else if (path.equals("/Logiweb/editDriver")) {
//                address = DriverService.handleEditDriver(request);
            } else {
                request.setAttribute("path", path);
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            }
            if (address != null) {
                request.getRequestDispatcher(address).forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuffer servletUrl = request.getRequestURL();

        if (servletUrl != null) {
            String address = null;
            URL url = new URL(servletUrl.toString());
            String path = url.getPath();
            if (path.equals("/Logiweb") || path.equals("/Logiweb/")) {
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else if (path.equals("/Logiweb/vehicles")) {
                List<VehicleEntity> vehicles = VehicleService.getListOfVehicles();
                request.setAttribute("list", vehicles);
                request.getRequestDispatcher("/WEB-INF/jsp/vehicles.jsp").forward(request, response);
                return;
            } else if (path.equals("/Logiweb/editVehicle")) {
                String vin = request.getParameter("vin");
                if (vin != null) {
                    VehicleEntity vehicle = VehicleService.getVehicle(vin);
                    if (vehicle != null) {
                        request.setAttribute("vin", vehicle.getVin());
                        request.setAttribute("capacity", vehicle.getCapacity());
                        request.setAttribute("numberOfDrivers", vehicle.getNumberOfDrivers());
                        request.setAttribute("isAvailable", ((Short) vehicle.getIsAvailable()).equals((short) 0) ? "no" : "yes");
                        request.setAttribute("vehicleCity", vehicle.getCity());
                        request.setAttribute("order", vehicle.getOrder());
                    }
                }
                List<CityEntity> cities = CityService.getListOfCities();
                request.setAttribute("cities", cities);
                request.getRequestDispatcher("/WEB-INF/jsp/editVehicle.jsp").forward(request, response);
                return;
            } else if (path.equals("/Logiweb/drivers")) {
                List<DriverEntity> drivers = DriverService.getListOfDrivers();
                request.setAttribute("list", drivers);
                request.getRequestDispatcher("/WEB-INF/jsp/drivers.jsp").forward(request, response);
                return;
            } else if (path.equals("/Logiweb/editDriver")) {
                String uid = request.getParameter("uid");
                if (uid != null) {
                    DriverEntity driver = DriverService.getDriver(uid);
                    if (driver != null) {
                        request.setAttribute("uid", driver.getUid());
                        request.setAttribute("monthHours", driver.getMonthHours());
                        request.setAttribute("driverCity", driver.getCity());
                        request.setAttribute("status", driver.getStatus());
                        request.setAttribute("firstName", driver.getUser().getFirstName());
                        request.setAttribute("lastName", driver.getUser().getLastName());
                    }
                }
                List<CityEntity> cities = CityService.getListOfCities();
                request.setAttribute("cities", cities);
                request.getRequestDispatcher("/WEB-INF/jsp/editDriver.jsp").forward(request, response);
                return;
            } else if (path.equals("/Logiweb/orders")) {
                List<OrderEntity> orders = OrderService.getListOfOrders();
                request.setAttribute("list", orders);
                request.getRequestDispatcher("/WEB-INF/jsp/orders.jsp").forward(request, response);
            } else if (path.equals("/Logiweb/editOrder")) {
                String uid = request.getParameter("uid");
                if (uid != null && !uid.isEmpty()) {

                }
                request.getRequestDispatcher("/WEB-INF/jsp/editOrder.jsp").forward(request, response);
            } else {
                request.setAttribute("path", path);
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            }
            if (address != null) {
                request.getRequestDispatcher(address).forward(request, response);
            }
        }
    }

}
