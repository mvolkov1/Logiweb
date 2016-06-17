package com.tsystems.logiweb.dao;

import com.tsystems.logiweb.dao.api.*;
import com.tsystems.logiweb.dao.entity.*;
import com.tsystems.logiweb.dao.impl.*;
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

    private String testCity = "TestCity";
    private final int numberOfCities = 4;

    private float distance = 100.1f;
    private final float distIncrement = 23.5f;

    private float capacity = 35.1f;
    private final float capacityIncrement = 1.3f;
    private int vinNumber1 = 15320;
    private int vinNumber2 = 24971;
    private int numberOfDrivers = 1;

    private CityDao cityDao;
    private DistanceDao distanceDao;
    private VehicleDao vehicleDao;
    private DriverDao driverDao;
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;
    private CargoDao cargoDao;

    @Before
    public void before() throws Exception{
        cityDao = new CityDaoImpl();
        distanceDao = new DistanceDaoImpl();
        vehicleDao = new VehicleDaoImpl();
        driverDao = new DriverDaoImpl();
        orderDao = new OrderDaoImpl();
        orderItemDao = new OrderItemDaoImpl();
        cargoDao = new CargoDaoImpl();
    }

    @Test
    public void makeDaoTest() throws Exception{
        createCities();
        findCitiesByName();
        createDistance();
        createVehicles();
       // checkVehicles();
        createDrivers();
        createOrder();
    }


    public void createCities() throws Exception{
        for (int i=0; i < numberOfCities; i++)
        {
            CityEntity city = new CityEntity(testCity + "_" + i);
            cityDao.save(city);
        }

    }




    public void findCitiesByName() throws Exception{
        for (int i=0; i < numberOfCities; i++)
        {
            CityEntity city = cityDao.findByName(testCity + "_" + i);
            Assert.assertNotNull(city);
        }

    }


    public void createDistance() throws Exception{

        Iterator it1 = cityDao.getAllEntities(CityEntity.class).iterator();
        while(it1.hasNext())
        {
            CityEntity city1 = (CityEntity) it1.next();
            Iterator it2 = cityDao.getAllEntities(CityEntity.class).iterator();
            while(it2.hasNext())
            {
                CityEntity city2 = (CityEntity) it2.next();
                if (city1.getCity().equals(city2.getCity()))
                    break;

                DistanceEntity distanceEntity = new DistanceEntity();
                distanceEntity.setCity1(city1);
                distanceEntity.setCity2(city2);
                distanceEntity.setDistance(new BigDecimal(distance));
                distanceDao.save(distanceEntity);

                distanceEntity = new DistanceEntity();
                distanceEntity.setCity1(city2);
                distanceEntity.setCity2(city1);
                distanceEntity.setDistance(new BigDecimal(distance));
                distanceDao.save(distanceEntity);

                distance += distIncrement;
            }
        }
    }


    public void createVehicles() {

        Iterator it = cityDao.getAllEntities(CityEntity.class).iterator();
        int i = 0;
        boolean cityHasTwoVehicles = false;
        while(it.hasNext())
        {
            CityEntity city = (CityEntity) it.next();

            VehicleEntity vehicle = new VehicleEntity();
            vehicle.setVin("AD" + vinNumber1);
            vehicle.setCapacity(new BigDecimal(capacity));
            vehicle.setNumberOfDrivers(numberOfDrivers);
            vehicle.setIsAvailable((short)1);
            vehicle.setCity(city);

            vehicleDao.save(vehicle);
            i++;
            vinNumber1 += i;
            numberOfDrivers = (i%2 == 0) ? 1 : 2;
            capacity += capacityIncrement;

            if (cityHasTwoVehicles)
            {
                VehicleEntity anotherVehicle = new VehicleEntity();
                anotherVehicle.setVin("FT" + vinNumber2);
                anotherVehicle.setCapacity(new BigDecimal(capacity));
                anotherVehicle.setNumberOfDrivers(numberOfDrivers);
                anotherVehicle.setIsAvailable((short)1);
                anotherVehicle.setCity(city);

                i++;
                vinNumber2 += i;
                numberOfDrivers = (i%2 == 0) ? 1 : 2;
                capacity += capacityIncrement;

                vehicleDao.save(anotherVehicle);

            }

            cityHasTwoVehicles = !cityHasTwoVehicles;
        }

    }

    public void checkVehicles()
    {
        List<CityEntity> cities = cityDao.getAllEntities(CityEntity.class);
        Iterator it = cities.iterator();
        int i = 0;
        boolean cityHasTwoVehicles = false;
        while(it.hasNext()) {
            CityEntity city = (CityEntity) it.next();
            Collection<VehicleEntity> vehicles = city.getVehicles();
            Assert.assertNotNull(vehicles);
            for (VehicleEntity vehicleEntity : vehicles)
            {
                System.out.println(vehicleEntity.getVin() + " capacity = "
                        + vehicleEntity.getCapacity() + " number of drivers = " +
                        vehicleEntity.getNumberOfDrivers()
                );
            }

        }
    }


    public void createDrivers()
    {
        Iterator it = cityDao.getAllEntities(CityEntity.class).iterator();
        int i = 0;
        boolean cityHasTwoVehicles = false;
        while(it.hasNext())
        {
            CityEntity city = (CityEntity) it.next();
            DriverEntity driver = new DriverEntity();
            driver.setUid("12571256" + i);
            driver.setFirstName("John");
            driver.setLastName("Smith");
            driver.setMonthHours(0);
            driver.setCity(city);
            driver.setStatus("Free");
            driverDao.save(driver);
            i++;
        }
    }

    public void createOrder()
    {
        OrderEntity order = new OrderEntity();
        order.setUid("1291258");
        order.setIsCompleted((short)0);
        order.setNumberOfItems(3);

        OrderItemEntity item1 = new OrderItemEntity();
        item1.setItemNumber(1);
        item1.setOrder(order);
        item1.setCity(cityDao.findByName("TestCity_1"));

        OrderItemEntity item2 = new OrderItemEntity();
        item2.setItemNumber(2);
        item2.setOrder(order);
        item2.setCity(cityDao.findByName("TestCity_0"));

        OrderItemEntity item3 = new OrderItemEntity();
        item3.setItemNumber(3);
        item3.setOrder(order);
        item3.setCity(cityDao.findByName("TestCity_3"));

        CargoEntity cargo1 = new CargoEntity();
        cargo1.setOrder(order);
        cargo1.setUid("1258358");
        cargo1.setStatus("Prepared");
        cargo1.setStartCity(cityDao.findByName("TestCity_1"));
        cargo1.setEndCity(cityDao.findByName("TestCity_0"));
        cargo1.setMass(new BigDecimal(10.2));
        cargo1.setTitle("Computers");

        CargoEntity cargo2 = new CargoEntity();
        cargo2.setOrder(order);
        cargo2.setUid("0812567");
        cargo2.setStatus("Prepared");
        cargo2.setStartCity(cityDao.findByName("TestCity_1"));
        cargo2.setEndCity(cityDao.findByName("TestCity_3"));
        cargo2.setMass(new BigDecimal(12.3));
        cargo2.setTitle("Tables");

        CargoEntity cargo3 = new CargoEntity();
        cargo3.setOrder(order);
        cargo3.setUid("7154129");
        cargo3.setStatus("Prepared");
        cargo3.setStartCity(cityDao.findByName("TestCity_0"));
        cargo3.setEndCity(cityDao.findByName("TestCity_3"));
        cargo3.setMass(new BigDecimal(7.8));
        cargo3.setTitle("Chairs");



        orderDao.save(order);
        orderItemDao.save(item1);
        orderItemDao.save(item2);
        orderItemDao.save(item3);
        cargoDao.save(cargo1);
        cargoDao.save(cargo2);
        cargoDao.save(cargo3);

        List<VehicleEntity> vehicles = vehicleDao.getAllEntities(VehicleEntity.class);
        Iterator<VehicleEntity> it = vehicles.iterator();
        while (it.hasNext())
        {
            VehicleEntity vehicle = it.next();
            if (vehicle.getCity().getCity().equals(item1.getCity().getCity()))
            {
                vehicleDao.setOrder(vehicle, item1.getOrder());
                break;
            }
        }


    }

}
