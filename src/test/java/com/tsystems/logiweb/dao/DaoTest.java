package com.tsystems.logiweb.dao;

import com.tsystems.logiweb.dao.api.*;
import com.tsystems.logiweb.dao.entity.*;
import com.tsystems.logiweb.dao.impl.*;
import com.tsystems.logiweb.services.impl.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * Created by mvolkov on 13.06.2016.
 */
public class DaoTest {


    private float distance = 100.1f;
    private final float distIncrement = 23.5f;

    private float capacity = 35.1f;
    private final float capacityIncrement = 1.3f;
    private int vinNumber1 = 15320;
    private int vinNumber2 = 24971;
    private int numberOfDrivers = 1;


    private CityService cityService = new CityService();
    private DistanceService distanceService = new DistanceService();
    private VehicleService vehicleService = new VehicleService();
    private DriverService driverService = new DriverService();
    private UserService userService = new UserService();

    //    @Before
//    public void before() throws Exception{
//        cityDao = new CityDaoImpl();
//        distanceDao = new DistanceDaoImpl();
//        vehicleDao = new VehicleDaoImpl();
//        driverDao = new DriverDaoImpl();
//        orderDao = new OrderDaoImpl();
//        orderItemDao = new OrderItemDaoImpl();
//        cargoDao = new CargoDaoImpl();
//    }
//
    @Test
    public void makeDaoTest() throws Exception {
        createCities();
        createDistances();
        createUsers();
        createVehicles();
//        createOrder();
    }

    public void createUsers() throws Exception {
        userService.addManager("vkarpin", "12345", "manager", "Valery", "Karpin");
        userService.addManager("kberdiev", "12415", "manager", "Kurban", "Berdiev");
        userService.addManager("gorlov", "53161", "manager", "Gennady", "Orlov");

        driverService.addDriver("aarshavin", "12125", "Andrei", "Arshavin", "12495267", 0, "free", "St Petersburg");
        driverService.addDriver("akerzhakov", "12525", "Alexandr", "Kerzhakov", "13596199", 0, "free", "St Petersburg");
        driverService.addDriver("aanyukov", "16312", "Alexandr", "Anyukov", "24575622", 0, "free", "St Petersburg");
        driverService.addDriver("atikhonov", "81236", "Andrei", "Tikhonov", "34623471", 0, "free", "St Petersburg");
        driverService.addDriver("vberezutsky", "36734", "Vasily", "Berezutsky", "47147825", 0, "free", "St Petersburg");
        driverService.addDriver("signashevich", "98336", "Sergei", "Ignashevich", "25725828", 0, "free", "St Petersburg");
        driverService.addDriver("iakinfeev", "14364", "Igor", "Akinfeev", "25892926", 0, "free", "St Petersburg");
        driverService.addDriver("idenisov", "06513", "Igor", "Denisov", "25825825", 0, "free", "St Petersburg");
        driverService.addDriver("adzyuba", "51883", "Artem", "Dzyuba", "25835693", 0, "free", "St Petersburg");
        driverService.addDriver("vmalafeev", "35135", "Vyacheslav", "Malafeev", "36969225", 0, "free", "St Petersburg");
        driverService.addDriver("atimoschuk", "35133", "Anatoly", "Timoschuk", "35696359", 0, "free", "St Petersburg");

    }

    //
    public void createCities() throws Exception {

        cityService.addCity("Moscow");
        cityService.addCity("St Petersburg");
        cityService.addCity("Tver");
        cityService.addCity("Voronezh");
        cityService.addCity("Kazan");
        cityService.addCity("Saratov");
        cityService.addCity("Samara");
        cityService.addCity("Volgograd");
        cityService.addCity("Nizhny Novgorod");
        cityService.addCity("Vologda");
        cityService.addCity("Yaroslavl");
        cityService.addCity("Cherepovetz");
        cityService.addCity("Veliky Novgorod");

    }


    public void createDistances() throws Exception {

        distanceService.addDistance("Moscow", "St Petersburg", 700.0f);
        distanceService.addDistance("St Petersburg", "Moscow", 700.0f);

        distanceService.addDistance("Moscow", "Tver", 175.5f);
        distanceService.addDistance("Tver", "Moscow", 175.5f);

        distanceService.addDistance("Moscow", "Voronezh", 523.6f);
        distanceService.addDistance("Voronezh", "Moscow", 523.6f);

        distanceService.addDistance("Moscow", "Saratov", 844.1f);
        distanceService.addDistance("Saratov", "Moscow", 844.1f);

        distanceService.addDistance("Moscow", "Samara", 1045.5f);
        distanceService.addDistance("Samara", "Moscow", 1045.5f);

        distanceService.addDistance("Moscow", "Volgograd", 969.3f);
        distanceService.addDistance("Volgograd", "Moscow", 969.3f);

        distanceService.addDistance("Moscow", "Nizhny Novgorod", 416.9f);
        distanceService.addDistance("Nizhny Novgorod", "Moscow", 416.9f);

        distanceService.addDistance("Moscow", "Yaroslavl", 264.2f);
        distanceService.addDistance("Yaroslavl", "Moscow", 264.2f);

        distanceService.addDistance("St Petersburg", "Cherepovetz", 538.1f);
        distanceService.addDistance("Cherepovetz", "St Petersburg", 538.1f);

        distanceService.addDistance("St Petersburg", "Veliky Novgorod", 194.0f);
        distanceService.addDistance("Veliky Novgorod", "St Petersburg", 194.0f);

        distanceService.addDistance("Tver", "Veliky Novgorod", 362.1f);
        distanceService.addDistance("Veliky Novgorod", "Tver", 362.1f);

        distanceService.addDistance("Voronezh", "Saratov", 512.7f);
        distanceService.addDistance("Saratov", "Voronezh", 512.7f);

        distanceService.addDistance("Voronezh", "Volgograd", 575.2f);
        distanceService.addDistance("Volgograd", "Voronezh", 575.2f);

        distanceService.addDistance("Kazan", "Saratov", 667.6f);
        distanceService.addDistance("Saratov", "Kazan", 667.6f);

        distanceService.addDistance("Kazan", "Nizhny Novgorod", 388.7f);
        distanceService.addDistance("Nizhny Novgorod", "Kazan", 388.7f);

        distanceService.addDistance("Saratov", "Volgograd", 377.5f);
        distanceService.addDistance("Volgograd", "Saratov", 377.5f);

        distanceService.addDistance("Vologda", "Yaroslavl", 194.1f);
        distanceService.addDistance("Yaroslavl", "Vologda", 194.1f);

        distanceService.addDistance("Vologda", "Cherepovetz", 135.6f);
        distanceService.addDistance("Cherepovetz", "Vologda", 135.6f);


    }


    public void createVehicles() {
        vehicleService.addVehicle("KF91284", 38f, 2, (short) 1, "Moscow");
        vehicleService.addVehicle("AA23525", 35f, 1, (short) 1, "Moscow");
        vehicleService.addVehicle("AS09125", 35f, 1, (short) 1, "Moscow");
        vehicleService.addVehicle("PD23523", 38f, 2, (short) 1, "St Petersburg");
        vehicleService.addVehicle("AP42342", 40f, 2, (short) 1, "St Petersburg");
        vehicleService.addVehicle("SR32152", 35f, 2, (short) 1, "St Petersburg");
        vehicleService.addVehicle("SA23532", 38f, 2, (short) 1, "Voronezh");
        vehicleService.addVehicle("FF32523", 35f, 1, (short) 1, "Voronezh");
        vehicleService.addVehicle("JF41243", 35f, 1, (short) 1, "Kazan");
        vehicleService.addVehicle("KF32462", 35f, 1, (short) 1, "Nizhny Novgorod");
        vehicleService.addVehicle("DS23152", 40f, 2, (short) 1, "Nizhny Novgorod");
        vehicleService.addVehicle("RT32432", 35f, 1, (short) 1, "Samara");
        vehicleService.addVehicle("KG23423", 38f, 2, (short) 1, "Samara");
        vehicleService.addVehicle("DG19241", 35f, 1, (short) 1, "Samara");
        vehicleService.addVehicle("WE71474", 35f, 1, (short) 1, "Vologda");
    }

//    public void checkVehicles()
//    {
//        List<CityEntity> cities = cityDao.getAllEntities(CityEntity.class);
//        Iterator it = cities.iterator();
//        int i = 0;
//        boolean cityHasTwoVehicles = false;
//        while(it.hasNext()) {
//            CityEntity city = (CityEntity) it.next();
//            Collection<VehicleEntity> vehicles = city.getVehicles();
//            Assert.assertNotNull(vehicles);
//            for (VehicleEntity vehicleEntity : vehicles)
//            {
//                System.out.println(vehicleEntity.getVin() + " capacity = "
//                        + vehicleEntity.getCapacity() + " number of drivers = " +
//                        vehicleEntity.getNumberOfDrivers()
//                );
//            }
//
//        }
//    }

//
//    public void createOrder()
//    {
//        OrderEntity order = new OrderEntity();
//        order.setUid("1291258");
//        order.setIsCompleted((short)0);
//        order.setNumberOfItems(3);
//
//        OrderItemEntity item1 = new OrderItemEntity();
//        item1.setItemNumber(1);
//        item1.setOrder(order);
//        item1.setCity(cityDao.findByName("TestCity_1"));
//
//        OrderItemEntity item2 = new OrderItemEntity();
//        item2.setItemNumber(2);
//        item2.setOrder(order);
//        item2.setCity(cityDao.findByName("TestCity_0"));
//
//        OrderItemEntity item3 = new OrderItemEntity();
//        item3.setItemNumber(3);
//        item3.setOrder(order);
//        item3.setCity(cityDao.findByName("TestCity_3"));
//
//        CargoEntity cargo1 = new CargoEntity();
//        cargo1.setOrder(order);
//        cargo1.setUid("1258358");
//        cargo1.setStatus("Prepared");
//        cargo1.setStartCity(cityDao.findByName("TestCity_1"));
//        cargo1.setEndCity(cityDao.findByName("TestCity_0"));
//        cargo1.setMass(new BigDecimal(10.2));
//        cargo1.setTitle("Computers");
//
//        CargoEntity cargo2 = new CargoEntity();
//        cargo2.setOrder(order);
//        cargo2.setUid("0812567");
//        cargo2.setStatus("Prepared");
//        cargo2.setStartCity(cityDao.findByName("TestCity_1"));
//        cargo2.setEndCity(cityDao.findByName("TestCity_3"));
//        cargo2.setMass(new BigDecimal(12.3));
//        cargo2.setTitle("Tables");
//
//        CargoEntity cargo3 = new CargoEntity();
//        cargo3.setOrder(order);
//        cargo3.setUid("7154129");
//        cargo3.setStatus("Prepared");
//        cargo3.setStartCity(cityDao.findByName("TestCity_0"));
//        cargo3.setEndCity(cityDao.findByName("TestCity_3"));
//        cargo3.setMass(new BigDecimal(7.8));
//        cargo3.setTitle("Chairs");
//
//
//
//        orderDao.save(order);
//        orderItemDao.save(item1);
//        orderItemDao.save(item2);
//        orderItemDao.save(item3);
//        cargoDao.save(cargo1);
//        cargoDao.save(cargo2);
//        cargoDao.save(cargo3);
//
//        List<VehicleEntity> vehicles = vehicleDao.getAllEntities(VehicleEntity.class);
//        Iterator<VehicleEntity> it = vehicles.iterator();
//        while (it.hasNext())
//        {
//            VehicleEntity vehicle = it.next();
//            if (vehicle.getCity().getCity().equals(item1.getCity().getCity()))
//            {
//                vehicleDao.setOrder(vehicle, item1.getOrder());
//                break;
//            }
//        }
//
//
//    }

}
