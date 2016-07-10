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
import java.util.*;
import java.util.stream.Collectors;

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

    public static void deleteOrder(String uid) {
        if (uid != null && !uid.isEmpty()) {
            OrderDao orderDao = new OrderDaoImpl(TransactionManager.getEntityManager());
            try {
                TransactionManager.beginTransaction();
                orderDao.deleteByUid(uid);
                TransactionManager.commit();
            } catch (Exception e) {
                TransactionManager.rollback();
            }
        }
    }

    public static void createOrder(String uid) {
        if (uid != null && !uid.isEmpty()) {
            OrderDao orderDao = new OrderDaoImpl(TransactionManager.getEntityManager());
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

    public static void deleteItem(String uid, String itemNumber) {
        int nItem = 0;
        try {
            nItem = Integer.parseInt(itemNumber);
        } catch (NumberFormatException e) {
            return;
        }
        OrderDao orderDao = new OrderDaoImpl(TransactionManager.getEntityManager());
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

    public static void deleteCargo(String uid, String cargoUid) {
        OrderDao orderDao = new OrderDaoImpl(TransactionManager.getEntityManager());
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

    public static void setVehicle(String uid, String vin) {
        OrderDao orderDao = new OrderDaoImpl(TransactionManager.getEntityManager());
        VehicleDao vehicleDao = new VehicleDaoImpl(TransactionManager.getEntityManager());
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            VehicleEntity vehicle = vehicleDao.findByVin(vin);
            if (order.getVehicle() != null) {
                VehicleEntity oldVehicle = order.getVehicle();
                oldVehicle.setOrder(null);
            }
            vehicle.setOrder(order);
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

    public static void addDriver(String uid, String driverUid) {
        OrderDao orderDao = new OrderDaoImpl(TransactionManager.getEntityManager());
        DriverDao driverDao = new DriverDaoImpl(TransactionManager.getEntityManager());
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = orderDao.findByUid(uid);
            DriverEntity driver = driverDao.findByUid(driverUid);
//            if (order.getVehicle() != null) {
//                VehicleEntity oldVehicle = order.getVehicle();
//                oldVehicle.setOrder(null);
//            }
            driver.setOrder(order);
            //   vehicle.setOrder(order);
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }


    public static List<OrderEntity> getListOfOrders() {
        List<OrderEntity> orders = null;
        try {
            TransactionManager.beginTransaction();
            orders = new OrderDaoImpl(TransactionManager.getEntityManager()).getAllEntities(OrderEntity.class);
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

    public static OrderEntity getOrder(String uid) {
        OrderEntity order = null;
        try {
            TransactionManager.beginTransaction();
            order = new OrderDaoImpl(TransactionManager.getEntityManager()).findByUid(uid);
            order.getItems().size();
            TransactionManager.commit();
        } catch (Exception e) {
            order = null;
        }
        return order;
    }

    public static void addItem(String uid, String cityName) {
        List<OrderItemEntity> items = OrderService.getListOfItems(uid);
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = new OrderDaoImpl(TransactionManager.getEntityManager()).findByUid(uid);
            DistanceDao distanceDao = new DistanceDaoImpl(TransactionManager.getEntityManager());
            CityEntity city = new CityDaoImpl(TransactionManager.getEntityManager()).findByName(cityName);
            if (order != null && city != null) {
                OrderItemEntity item = new OrderItemEntity();
                int nItems = items.size();
                item.setItemNumber(nItems + 1);
                item.setCity(city);
                item.setOrder(order);
                OrderItemEntity prevItem = null;
                if (nItems > 1) {
                    prevItem = items.get(nItems - 1);
                    CityEntity city1 = item.getCity();
                    CityEntity city2 = prevItem.getCity();
                    DistanceEntity distanceEntity = distanceDao.findDistance(city1, city2);
                    BigDecimal fullDistance = prevItem.getFullDistance().add(distanceEntity.getDistance());
                    item.setDistance(distanceEntity.getDistance());
                    item.setFullDistance(fullDistance);
                } else {
                    item.setDistance(new BigDecimal(0));
                    item.setFullDistance(new BigDecimal(0));
                }
                order.getItems().add(item);
            }
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollback();
        }
    }

    public static void addCargo(String uid, String cargoUID, String title, String mass,
                                String cargoItem1, String cargoItem2, String status) {
        OrderDao orderDao = new OrderDaoImpl(TransactionManager.getEntityManager());
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
                BigDecimal maxMass = OrderService.getMaxMass(order);
                VehicleEntity vehicle = order.getVehicle();
                if (vehicle != null)
                {
                    if (vehicle.getCapacity().compareTo(maxMass) < 0)
                    {
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

    public static List<OrderItemEntity> getListOfItems(String uid) {
        List<OrderItemEntity> items = null;
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = new OrderDaoImpl(TransactionManager.getEntityManager()).findByUid(uid);
            if (order != null) {
                items = (List<OrderItemEntity>) order.getItems();
                DistanceDao distanceDao = new DistanceDaoImpl(TransactionManager.getEntityManager());
                OrderItemEntity prevItem = null;
                for (OrderItemEntity item : items) {
                    if (prevItem != null) {
                        CityEntity city1 = item.getCity();
                        CityEntity city2 = prevItem.getCity();
                        DistanceEntity distanceEntity = distanceDao.findDistance(city1, city2);
                        BigDecimal fullDistance = prevItem.getFullDistance().add(distanceEntity.getDistance());
                        item.setDistance(distanceEntity.getDistance());
                        item.setFullDistance(fullDistance);
                    } else {
                        item.setDistance(new BigDecimal(0));
                        item.setFullDistance(new BigDecimal(0));
                    }
                    prevItem = item;
                }
            }
            TransactionManager.commit();
        } catch (Exception e) {
            items = null;
            TransactionManager.rollback();
        }

        return items;
    }

    public static Set<CityEntity> getListOfCities(String uid) {
        Set<CityEntity> cities = new HashSet<>();
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = new OrderDaoImpl(TransactionManager.getEntityManager()).findByUid(uid);
            if (order != null) {
                int nItems = order.getItems().size();
                if (nItems > 0) {
                    CityEntity lastCityEntity = ((List<OrderItemEntity>) order.getItems()).get(nItems - 1).getCity();
                    if (lastCityEntity != null) {
                        String lastCity = lastCityEntity.getName();
                        List<DistanceEntity> distances = new DistanceDaoImpl(TransactionManager.getEntityManager()).getAllEntities(DistanceEntity.class);
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
                    CityDao cityDao = new CityDaoImpl(TransactionManager.getEntityManager());
                    List<CityEntity> allCities = (List<CityEntity>) cityDao.getAllEntities(CityEntity.class);
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


    public static List<CargoEntity> getListOfCargoes(String uid) {
        List<CargoEntity> cargoes = null;
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = new OrderDaoImpl(TransactionManager.getEntityManager()).findByUid(uid);
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

    public static List<VehicleEntity> getListOfVehicles(String uid) {
        List<VehicleEntity> vehicles = new VehicleDaoImpl(TransactionManager.getEntityManager())
                .getAllEntities(VehicleEntity.class);
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = new OrderDaoImpl(TransactionManager.getEntityManager()).findByUid(uid);

            BigDecimal maxMass = OrderService.getMaxMass(order);

            Iterator<VehicleEntity> iterator = vehicles.iterator();
            while (iterator.hasNext()) {
                VehicleEntity vehicleEntity = iterator.next();
                if (vehicleEntity.getCapacity().compareTo(maxMass) < 0) {
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

    protected static BigDecimal getMaxMass(OrderEntity order) throws  Exception
    {
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

    public static List<DriverEntity> getListOfDrivers(String uid) {
        List<DriverEntity> drivers = new ArrayList<>();
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = new OrderDaoImpl(TransactionManager.getEntityManager()).findByUid(uid);
            if (order != null) {
                drivers.addAll(order.getDrivers());
            }
            TransactionManager.commit();
        } catch (Exception e) {
            drivers = null;
            TransactionManager.rollback();
        }
        return drivers;
    }

    public static List<DriverEntity> getListOfPossibleDrivers(String uid) {
        List<DriverEntity> drivers = new ArrayList<>();
        List<DriverEntity> allDrivers = new DriverDaoImpl(TransactionManager.getEntityManager()).
                getAllEntities(DriverEntity.class);
        try {
            TransactionManager.beginTransaction();
            OrderEntity order = new OrderDaoImpl(TransactionManager.getEntityManager()).findByUid(uid);
//            drivers = allDrivers.stream().filter(order.getDrivers()::contains).collect(Collectors.toList());
            for (DriverEntity driver : allDrivers) {
                if (!order.getDrivers().contains(driver)) {
                    drivers.add(driver);
                }
            }
            TransactionManager.commit();
        } catch (Exception e) {
            drivers = null;
            TransactionManager.rollback();
        }
        return drivers;
    }


}
