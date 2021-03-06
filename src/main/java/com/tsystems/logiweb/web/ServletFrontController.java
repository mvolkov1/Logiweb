package com.tsystems.logiweb.web;

import com.tsystems.logiweb.dao.entity.*;
import com.tsystems.logiweb.services.TransactionManager;
import com.tsystems.logiweb.services.impl.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;


/**
 * Created by mvolkov on 15.06.2016.
 */
public class ServletFrontController extends HttpServlet {

    private OrderService orderService = new OrderService();
    private UserService userService = new UserService();
    private VehicleService vehicleService = new VehicleService();
    private DriverService driverService = new DriverService();
    private CityService cityService = new CityService();
    private DistanceService distanceService = new DistanceService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuffer servletUrl = request.getRequestURL();
        orderService.setEntityManager(TransactionManager.getEntityManager());
        userService.setEntityManager(TransactionManager.getEntityManager());
        vehicleService.setEntityManager(TransactionManager.getEntityManager());
        driverService.setEntityManager(TransactionManager.getEntityManager());
        cityService.setEntityManager(TransactionManager.getEntityManager());
        distanceService.setEntityManager(TransactionManager.getEntityManager());

        if (servletUrl != null) {
            URL url = new URL(servletUrl.toString());
            String path = url.getPath();
            String address = null;
            if (path.equals("/Logiweb/") || path.equals("/Logiweb")) {
                this.redirectLogin(request, response);
                return;
            } else if (path.equals("/Logiweb/vehicles") || path.equals("/Logiweb/editVehicle")) {
                this.redirectVehiclesPost(request, response);
            } else if (path.equals("/Logiweb/drivers") || path.equals("/Logiweb/editDriver")) {
                this.redirectDriversPost(request, response);
                return;
            } else if (path.equals("/Logiweb/editOrder")) {
                redirectEditOrderPost(request, response);
                return;
            } else if (path.equals("/Logiweb/cities")) {
                this.redirectCitiesPost(request, response);
                return;
            } else if (path.equals("/Logiweb/editCity")) {
                this.redirectEditCityPost(request, response);
                return;
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

        orderService.setEntityManager(TransactionManager.getEntityManager());
        vehicleService.setEntityManager(TransactionManager.getEntityManager());
        driverService.setEntityManager(TransactionManager.getEntityManager());
        cityService.setEntityManager(TransactionManager.getEntityManager());
        userService.setEntityManager(TransactionManager.getEntityManager());
        distanceService.setEntityManager(TransactionManager.getEntityManager());

        if (servletUrl != null) {
            String address = null;
            URL url = new URL(servletUrl.toString());
            String path = url.getPath();
            if (path.equals("/Logiweb") || path.equals("/Logiweb/")) {
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else if (path.equals("/Logiweb/vehicles")) {
                this.redirectVehiclesGet(request, response);
                return;
            } else if (path.equals("/Logiweb/editVehicle")) {
                this.redirectEditVehicleGet(request, response);
                return;
            } else if (path.equals("/Logiweb/drivers")) {
                this.redirectDriversGet(request, response);
                return;
            } else if (path.equals("/Logiweb/editDriver")) {
                this.redirectEditDriverGet(request, response);
                return;
            } else if (path.equals("/Logiweb/orders")) {
                this.redirectOrdersGet(request, response);
                return;
            } else if (path.equals("/Logiweb/editOrder")) {
                this.redirectEditOrderGet(request, response);
                return;
            } else if (path.equals("/Logiweb/cities")) {
                this.redirectCitiesGet(request, response);
                return;
            } else if (path.equals("/Logiweb/editCity")) {
                this.redirectEditCityGet(request, response);
                return;
            } else {
                request.setAttribute("path", path);
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
                return;
            }
        }
        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
    }


    private void redirectEditOrderPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        String step = request.getParameter("step");
        if (uid != null && !uid.isEmpty()) {
            if (step != null) {
                if (step.equals("uid")) {
                    orderService.createOrder(uid);
                } else if (step.equals("startTime")) {
                    String day = request.getParameter("day");
                    String month = request.getParameter("month");
                    String year = request.getParameter("year");
                    String hour = request.getParameter("hour");
                    orderService.setStartTime(uid, day, month, year, hour);
                } else if (step.equals("item")) {
                    String city = request.getParameter("city");
                    orderService.addItem(uid, city);
                } else if (step.equals("deleteItem")) {
                    String itemNumber = request.getParameter("itemNumber");
                    orderService.deleteItem(uid, itemNumber);
                } else if (step.equals("cargo")) {
                    String cargoUid = request.getParameter("cargoUid");
                    String title = request.getParameter("cargoTitle");
                    String mass = request.getParameter("cargoMass");
                    String cargoItem1 = request.getParameter("cargoItem1");
                    String cargoItem2 = request.getParameter("cargoItem2");
                    String status = request.getParameter("status");
                    orderService.addCargo(uid, cargoUid, title, mass, cargoItem1, cargoItem2, status);
                } else if (step.equals("deleteCargo")) {
                    String cargoUid = request.getParameter("cargoUid");
                    orderService.deleteCargo(uid, cargoUid);
                } else if (step.equals("vehicle")) {
                    String vin = request.getParameter("vin");
                    orderService.setVehicle(uid, vin);
                } else if (step.equals("deleteVehicle")) {
                    orderService.deleteVehicle(uid);
                } else if (step.equals("driver")) {
                    String driverUid = request.getParameter("driverUid");
                    orderService.addDriver(uid, driverUid);
                } else if (step.equals("deleteDriver")) {
                    String driverUid = request.getParameter("driverUid");
                    orderService.deleteDriver(uid, driverUid);
                }
            }
        }
        response.sendRedirect("editOrder?uid=" + uid);
    }



    public void redirectLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        String address = null;
        if (login != null && password != null) {
            StringBuilder firstName = new StringBuilder();
            StringBuilder lastName = new StringBuilder();
            address = userService.handleLoginPassword(login, password, firstName, lastName);
            if (address != null) {
                request.setAttribute("lastName", lastName.toString());
                request.setAttribute("firstName", firstName.toString());
                request.getRequestDispatcher(address).forward(request, response);
                return;
            }
        }
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    private void redirectVehiclesPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String saveVehicle = request.getParameter("saveVehicle");
        String vin = request.getParameter("vin");
        if (vin != null && !vin.isEmpty()) {
            if (saveVehicle != null) {
                String capacity = request.getParameter("capacity");
                String numberOfDrivers = request.getParameter("numberOfDrivers");
                String city = request.getParameter("city");
                String isAvailable = request.getParameter("isAvailable1");
                isAvailable = isAvailable.equals("no") ? "0" : "1";
                vehicleService.saveVehicle(vin, capacity, numberOfDrivers, city, isAvailable);
            }
        }
        response.sendRedirect("vehicles");
    }

    private void redirectDriversPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        String saveDriver = request.getParameter("saveDriver");
        if (uid != null && !uid.isEmpty()) {
            if (saveDriver != null) {
                String oldUid = request.getParameter("oldUid");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                String monthHours = request.getParameter("monthHours");
                String city = request.getParameter("city");
                String status = request.getParameter("status1");
                driverService.saveDriver(uid, firstName, lastName, login, password, oldUid, monthHours, status, city);
            }
        }
        response.sendRedirect("drivers");
    }

    private void redirectCitiesGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<CityEntity> cities = cityService.getListOfCities();
        request.setAttribute("list", cities);
        request.getRequestDispatcher("/WEB-INF/jsp/cities.jsp").forward(request, response);
    }

    private void redirectCitiesPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String newCity = request.getParameter("newCity");
        cityService.addCity(newCity);
        response.sendRedirect("cities");
    }

    private void redirectEditCityPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cityName = request.getParameter("cityName");
        String newNeighbor = request.getParameter("newNeighbor");
        String distance = request.getParameter("distance");
        distanceService.addDistance(cityName, newNeighbor, Float.parseFloat(distance));
        redirectEditCityGet(request,response);
    }

    private void redirectEditCityGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cityName = request.getParameter("cityName");
        Map<String, BigDecimal> neighbors  =cityService.getListOfNeighborCities(cityName);
        Set<CityEntity> possibleNeighbors = cityService.getListOfPossibleNeighbors(cityName);
        request.setAttribute("cityName", cityName);
        request.setAttribute("neighbors", neighbors);
        request.setAttribute("possibleNeighbors", possibleNeighbors);
        request.getRequestDispatcher("/WEB-INF/jsp/editCity.jsp").forward(request, response);
    }

    private void redirectVehiclesGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vin = request.getParameter("vin");
        if (vin != null && !vin.isEmpty()) {
            String deleteVehicle = request.getParameter("deleteVehicle");
            if (deleteVehicle != null) {
                vehicleService.deleteVehicle(vin);
            }
        }
        List<VehicleEntity> vehicles = vehicleService.getListOfVehicles();
        request.setAttribute("list", vehicles);
        request.getRequestDispatcher("/WEB-INF/jsp/vehicles.jsp").forward(request, response);
    }

    private void redirectDriversGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        if (uid != null && !uid.isEmpty()) {
            String deleteDriver = request.getParameter("deleteDriver");
            if (deleteDriver != null) {
                driverService.deleteDriver(uid);
            }
        }
        List<DriverEntity> drivers = driverService.getListOfDrivers();
        request.setAttribute("list", drivers);
        request.getRequestDispatcher("/WEB-INF/jsp/drivers.jsp").forward(request, response);
    }

    private void redirectEditVehicleGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vin = request.getParameter("vin");
        if (vin != null) {
            VehicleEntity vehicle = vehicleService.getVehicle(vin);
            if (vehicle != null) {
                request.setAttribute("vin", vehicle.getVin());
                request.setAttribute("capacity", vehicle.getCapacity());
                request.setAttribute("numberOfDrivers", vehicle.getNumberOfDrivers());
                request.setAttribute("isAvailable", ((Short) vehicle.getIsAvailable()).equals((short) 0) ? "no" : "yes");
                request.setAttribute("vehicleCity", vehicle.getCity().getName());
                request.setAttribute("order", vehicle.getOrder());
            }
            if (request.getParameter("deleteVehicle") != null) {
                request.setAttribute("deleteVehicle", "true");
                redirectVehiclesGet(request, response);
                return;
            }
        }
        if (request.getParameter("editVehicle") != null) {
            request.setAttribute("editVehicle", "true");
            List<CityEntity> cities = cityService.getListOfCities();
            request.setAttribute("cities", cities);
        }
        request.getRequestDispatcher("/WEB-INF/jsp/editVehicle.jsp").forward(request, response);
    }

    public void redirectEditDriverGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        if (uid != null) {
            DriverEntity driver = driverService.getDriver(uid);
            if (driver != null) {
                request.setAttribute("uid", driver.getUid());
                request.setAttribute("monthHours", driver.getMonthHours());
                request.setAttribute("driverCity", driver.getCity().getName());
                request.setAttribute("status", driver.getStatus());
                request.setAttribute("firstName", driver.getUser().getFirstName());
                request.setAttribute("lastName", driver.getUser().getLastName());
                request.setAttribute("login", driver.getUser().getLogin());
                request.setAttribute("password", driver.getUser().getPassword());
            }
            if (request.getParameter("deleteDriver") != null) {
                request.setAttribute("deleteDriver", "true");
                redirectDriversGet(request, response);
                return;
            }
        }
        if (request.getParameter("editDriver") != null) {
            request.setAttribute("editDriver", "true");
            List<CityEntity> cities = cityService.getListOfCities();
            request.setAttribute("cities", cities);
        }
        request.getRequestDispatcher("/WEB-INF/jsp/editDriver.jsp").forward(request, response);
    }



    public void redirectOrdersGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        String deleteOrder = request.getParameter("deleteOrder");
        if (uid != null && !uid.isEmpty()) {
            if (deleteOrder != null && deleteOrder.equals("true")) {
                orderService.deleteOrder(uid);
            }
        }
        List<OrderEntity> orders = orderService.getListOfOrders();
        request.setAttribute("list", orders);
        request.getRequestDispatcher("/WEB-INF/jsp/orders.jsp").forward(request, response);
    }


    public void redirectEditOrderGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        if (uid != null && !uid.isEmpty()) {
            OrderEntity orderEntity = orderService.getOrder(uid);
            if (orderEntity == null)
                return;
            request.setAttribute("uid", uid);
            Set<CityEntity> cities = orderService.getListOfCities(uid);
            request.setAttribute("cities", cities);
            List<OrderItemEntity> items = orderService.getListOfItems(uid);
            request.setAttribute("items", items);
            List<String> itemNumbers = new ArrayList<>();
            for (int i = 1; i <= orderEntity.getItems().size(); i++) {
                itemNumbers.add(Integer.toString(i));
            }
            request.setAttribute("itemNumbers", itemNumbers);
            List<CargoEntity> cargoes = orderService.getListOfCargoes(uid);
            request.setAttribute("cargoes", cargoes);
            List<String> statusList = new ArrayList<>();
            statusList.add("Prepared");
            statusList.add("Loaded");
            statusList.add("Delivered");
            request.setAttribute("statusList", statusList);
            request.setAttribute("nextItemNumber", orderEntity.getItems().size() + 1);
            int nItems = orderEntity.getItems().size();
            if (nItems > 0) {
                OrderItemEntity lastItem = ((List<OrderItemEntity>) orderEntity.getItems()).get(nItems - 1);
                CityEntity lastCity = lastItem.getCity();
                cities.remove(lastCity);
            }
            List<VehicleEntity> vehicles = orderService.getListOfVehicles(uid);
            request.setAttribute("vehicles", vehicles);
            VehicleEntity vehicle = orderEntity.getVehicle();
            request.setAttribute("vehicle", vehicle);
            List<DriverEntity> possibleDrivers = orderService.getListOfPossibleDrivers(uid);
            request.setAttribute("possibleDrivers", possibleDrivers);
            List<DriverEntity> drivers = orderService.getListOfDrivers(uid);
            request.setAttribute("drivers", drivers);

            List<String> days = new ArrayList<>();
            for (int i = 1; i <= 31; i++) {
                days.add(String.format("%02d", i));
            }
            request.setAttribute("days", days);
            List<String> months = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                months.add(String.format("%02d", i));
            }
            request.setAttribute("months", months);
            List<String> years = new ArrayList<>();
            for (int i = 2000; i <= 2100; i++) {
                years.add(String.format("%04d", i));
            }
            request.setAttribute("years", years);
            List<String> hours = new ArrayList<>();
            for (int i = 0; i <= 23; i++) {
                hours.add(String.format("%02d", i));
            }
            request.setAttribute("hours", hours);
            Date startTime = orderEntity.getStartTime();
            if (startTime == null) {
                startTime = Calendar.getInstance().getTime();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(startTime);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour = cal.get(Calendar.HOUR_OF_DAY);


            request.setAttribute("startDay", String.format("%02d", day));
            request.setAttribute("startMonth", String.format("%02d", month));
            request.setAttribute("startYear", String.format("%04d", year));
            request.setAttribute("startHour", String.format("%02d", hour));
            String endTime = orderService.getEndTime(uid);
            request.setAttribute("endTime", endTime);
        }
        request.getRequestDispatcher("/WEB-INF/jsp/editOrder.jsp").forward(request, response);
    }


}
