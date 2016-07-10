package com.tsystems.logiweb.services.impl;

import com.tsystems.logiweb.dao.api.*;
import com.tsystems.logiweb.dao.entity.*;
import com.tsystems.logiweb.dao.impl.*;
import com.tsystems.logiweb.services.TransactionManager;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mvolkov on 17.06.2016.
 */
public class OrderService extends BaseService {


    public void deleteOrder(String uid) {
        if (uid != null && !uid.isEmpty()) {
            try {
                TransactionManager.beginTransaction();
                orderDao.deleteByUid(uid);
                TransactionManager.commit();
            } catch (Exception e) {
                TransactionManager.rollback();
            }
        }
    }

    public void createOrder(String uid) {
        if (uid != null && !uid.isEmpty()) {
            try {
                TransactionManager.beginTransaction();
                OrderEntity orderEntity = orderDao.findByUid(uid);
                if (orderEntity == null) {
                    orderEntity = new OrderEntity();
                    orderEntity.setUid(uid);
                    orderDao.save(orderEntity);
                }
                TransactionManager.commit();
            } catch (Exception e) {
                TransactionManager.rollback();
            }
        }
    }

    public void setStartTime(String uid, String day, String month, String year, String hour) {
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            if (order != null) {
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HH");
                Date startTime = dateFormat.parse(year + month + day + "-" + hour);
                order.setStartTime(startTime);
            }
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

    public String getEndTime(String uid) {
        String endTime = "";
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            if (order != null) {
                Date startTime = order.getStartTime();
                if (order.getItems().size() > 0) {
                    this.calcDistanceAndTime((List<OrderItemEntity>) order.getItems());
                    int orderHours = ((List<OrderItemEntity>) order.getItems()).get(order.getItems().size() - 1).getFullTime();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startTime);
                    cal.add(Calendar.HOUR, orderHours);
                    Date newDate = cal.getTime();
                    DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    endTime = df.format(newDate);
                }
            }
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
        return endTime;
    }

    public void deleteItem(String uid, String itemNumber) {
        int nItem = 0;
        try {
            nItem = Integer.parseInt(itemNumber);
        } catch (NumberFormatException e) {
            return;
        }
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            int nItems = order.getItems().size();
            if (nItem <= 0 || nItem > nItems) {
                TransactionManager.rollback();
                return;
            }
            OrderItemEntity itemToDelete = null;
            for (OrderItemEntity item : order.getItems()) {
                if (itemToDelete != null) {
                    item.setItemNumber(item.getItemNumber() - 1);
                } else if (item.getItemNumber() == nItem) {
                    itemToDelete = item;
                    Iterator<CargoEntity> iterator = order.getCargos().iterator();
                    while (iterator.hasNext()) {
                        CargoEntity cargoEntity = iterator.next();
                        if (cargoEntity.getItemStart().getItemNumber() == nItem ||
                                cargoEntity.getItemEnd().getItemNumber() == nItem) {
                            iterator.remove();
                        }
                    }
                }
            }
            if (itemToDelete != null) {
                order.getItems().remove(itemToDelete);
            }
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

    public void deleteCargo(String uid, String cargoUid) {
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            for (CargoEntity cargo : order.getCargos()) {
                if (cargo.getUid().equals(cargoUid)) {
                    order.getCargos().remove(cargo);
                    break;
                }
            }
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

    public void setVehicle(String uid, String vin) {
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            VehicleEntity vehicle = vehicleDao.findByVin(vin);
            if (order.getVehicle() != null) {
                VehicleEntity oldVehicle = order.getVehicle();
                oldVehicle.setOrder(null);
                for (DriverEntity driver : order.getDrivers()) {
                    driver.setOrder(null);
                }
                order.getDrivers().clear();
            }
            vehicle.setOrder(order);
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

    public void deleteVehicle(String uid) {
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            VehicleEntity vehicle = order.getVehicle();
            vehicle.setOrder(null);
            order.setVehicle(null);
            for (DriverEntity driver : order.getDrivers()) {
                driver.setOrder(null);
            }
            order.getDrivers().clear();
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }


    public void addDriver(String uid, String driverUid) {
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            DriverEntity driver = driverDao.findByUid(driverUid);
            driver.setOrder(order);
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

    public void deleteDriver(String uid, String driverUid) {
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            DriverEntity driver = driverDao.findByUid(driverUid);
            driver.setOrder(null);
            order.getDrivers().remove(driver);
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }


    public List<OrderEntity> getListOfOrders() {
        List<OrderEntity> orders = null;
        try {
            TransactionManager.beginTransaction();
            orders = orderDao.getAllEntities(OrderEntity.class);
            for (OrderEntity order : orders) {
                order.getItems().size();
                order.getDrivers().size();
                order.getCargos().size();
            }
            TransactionManager.commit();
        } catch (Exception e) {
            orders = null;
        }
        return orders;
    }

    public OrderEntity getOrder(String uid) {
        OrderEntity order = null;
        try {
            TransactionManager.beginTransaction();
            order = orderDao.findByUid(uid);
            order.getItems().size();
            TransactionManager.commit();
        } catch (Exception e) {
            order = null;
        }
        return order;
    }


    public void addCargo(String uid, String cargoUID, String title, String mass,
                         String cargoItem1, String cargoItem2, String status) {
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            int index1 = Integer.parseInt(cargoItem1) - 1;
            int index2 = Integer.parseInt(cargoItem2) - 1;
            OrderItemEntity item1 = ((List<OrderItemEntity>) order.getItems()).get(index1);
            OrderItemEntity item2 = ((List<OrderItemEntity>) order.getItems()).get(index2);
            if (order != null && item1 != null && item2 != null) {
                CargoEntity cargo = new CargoEntity();
                cargo.setUid(cargoUID);
                cargo.setItemStart(item1);
                cargo.setItemEnd(item2);
                cargo.setTitle(title);
                cargo.setOrder(order);
                cargo.setMass(new BigDecimal(Integer.parseInt(mass)));
                cargo.setStatus(status);
                order.getCargos().add(cargo);
                BigDecimal maxMass = this.getMaxMass(order);
                VehicleEntity vehicle = order.getVehicle();
                if (vehicle != null) {
                    if (vehicle.getCapacity().compareTo(maxMass) < 0) {
                        vehicle.setOrder(null);
                        order.setVehicle(null);
                    }
                }
            }
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

    public void addItem(String uid, String cityName) {
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            CityEntity city = cityDao.findByName(cityName);
            if (order != null && city != null) {
                OrderItemEntity item = new OrderItemEntity();
                item.setItemNumber(order.getItems().size() + 1);
                item.setCity(city);
                item.setOrder(order);
                order.getItems().add(item);
                this.calcDistanceAndTime((List<OrderItemEntity>) order.getItems());
            }
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

    public List<OrderItemEntity> getListOfItems(String uid) {
        List<OrderItemEntity> items = null;
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = new OrderDaoImpl(TransactionManager.getEntityManager()).findByUid(uid);
            if (order != null) {
                items = (List<OrderItemEntity>) order.getItems();
                this.calcDistanceAndTime(items);
            }
            TransactionManager.commit();
        } catch (Exception e) {
            items = null;
            TransactionManager.rollback();
        }
        return items;
    }

    private void calcDistanceAndTime(List<OrderItemEntity> items) {
        OrderItemEntity prevItem = null;
        for (OrderItemEntity item : items) {
            if (prevItem != null) {
                CityEntity city1 = item.getCity();
                CityEntity city2 = prevItem.getCity();
                DistanceEntity distanceEntity = distanceDao.findDistance(city1, city2);
                BigDecimal fullDistance = prevItem.getFullDistance().add(distanceEntity.getDistance());
                item.setDistance(distanceEntity.getDistance());
                item.setFullDistance(fullDistance);
                item.setTime(distanceEntity.getDistance().intValue() / 60);
                item.setFullTime(fullDistance.intValue() / 60);
            } else {
                item.setDistance(new BigDecimal(0));
                item.setFullDistance(new BigDecimal(0));
            }
            prevItem = item;
        }
    }

    public Set<CityEntity> getListOfCities(String uid) {
        Set<CityEntity> cities = new HashSet<>();
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            if (order != null) {
                int nItems = order.getItems().size();
                if (nItems > 0) {
                    CityEntity lastCityEntity = ((List<OrderItemEntity>) order.getItems()).get(nItems - 1).getCity();
                    if (lastCityEntity != null) {
                        String lastCity = lastCityEntity.getName();
                        List<DistanceEntity> distances = distanceDao.getAllEntities(DistanceEntity.class);
                        for (DistanceEntity distance : distances) {
                            if (distance.getCity1().getName().equals(lastCity)) {
                                cities.add(distance.getCity2());
                            }
                            if (distance.getCity2().getName().equals(lastCity)) {
                                cities.add(distance.getCity1());
                            }
                        }
                    }
                } else {
                    List<CityEntity> allCities = cityDao.getAllEntities(CityEntity.class);
                    cities = new HashSet<>(allCities);
                }
            }
            TransactionManager.commit();
        } catch (Exception e) {
            cities = null;
            TransactionManager.rollback();
        }
        return cities;
    }


    public List<CargoEntity> getListOfCargoes(String uid) {
        List<CargoEntity> cargoes = null;
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            if (order != null) {
                cargoes = (List<CargoEntity>) order.getCargos();
                cargoes.size();
            }
            TransactionManager.commit();
        } catch (Exception e) {
            cargoes = null;
            TransactionManager.rollback();
        }
        return cargoes;
    }

    public List<VehicleEntity> getListOfVehicles(String uid) {
        List<VehicleEntity> vehicles = vehicleDao.getAllEntities(VehicleEntity.class);
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            BigDecimal maxMass = this.getMaxMass(order);
            Iterator<VehicleEntity> iterator = vehicles.iterator();
            while (iterator.hasNext()) {
                VehicleEntity vehicleEntity = iterator.next();
                if (vehicleEntity.getOrder() != null || vehicleEntity.getCapacity().compareTo(maxMass) < 0) {
                    iterator.remove();
                }
            }
            TransactionManager.commit();
        } catch (Exception e) {
            vehicles = null;
            TransactionManager.rollback();
        }
        return vehicles;
    }

    protected BigDecimal getMaxMass(OrderEntity order) throws Exception {
        BigDecimal maxMass = new BigDecimal(0);
        BigDecimal currentMass = new BigDecimal(0);

        for (int i = 1; i <= order.getItems().size(); i++) {
            Iterator<CargoEntity> iterator = order.getCargos().iterator();
            while (iterator.hasNext()) {
                CargoEntity cargo = iterator.next();
                if (cargo.getItemStart().getItemNumber() == i) {
                    currentMass = currentMass.add(cargo.getMass());
                }
                if (cargo.getItemEnd().getItemNumber() == i) {
                    currentMass = currentMass.subtract(cargo.getMass());
                }
            }
            if (currentMass.compareTo(maxMass) > 0) {
                maxMass = currentMass;
            }
        }
        return maxMass;
    }

    public List<DriverEntity> getListOfPossibleDrivers(String uid) {
        List<DriverEntity> drivers = new ArrayList<>();
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            if (order != null) {
                VehicleEntity vehicle = order.getVehicle();
                if (vehicle != null && vehicle.getNumberOfDrivers() > order.getDrivers().size()) {
                    for (DriverEntity driver : driverDao.getFreeDrivers()) {
                        if (driver.getCity().getName().equals(vehicle.getCity().getName())) {
                            drivers.add(driver);
                        }
                    }
                }
            }
            TransactionManager.commit();
        } catch (Exception e) {
            drivers = null;
            TransactionManager.rollback();
        }
        return drivers;
    }

    public List<DriverEntity> getListOfDrivers(String uid) {
        List<DriverEntity> drivers = new ArrayList<>();
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            if (order != null) {
                order.getDrivers().size();
                drivers = (List<DriverEntity>) order.getDrivers();
            }
            TransactionManager.commit();
        } catch (Exception e) {
            drivers = null;
            TransactionManager.rollback();
        }
        return drivers;
    }


}
