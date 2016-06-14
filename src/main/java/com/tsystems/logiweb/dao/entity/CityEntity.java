package com.tsystems.logiweb.dao.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mvolkov on 12.06.2016.
 */
@Entity
@Table(name = "city", schema = "logiweb", catalog = "")
@Access(AccessType.PROPERTY)
public class CityEntity {
    private long id;
    private String city;
    private Collection<VehicleEntity> vehicles;
    private Collection<DriverEntity> drivers;
    private Collection<OrderItemEntity> orderItems;


    public CityEntity(){super();}

    public CityEntity(String city){this.city = city;}

    @Id@GeneratedValue
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "city", nullable = false, length = 50, unique = true)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @OneToMany(mappedBy = "city")
    public Collection<DriverEntity> getDrivers() {
        return drivers;
    }

    public void setDrivers(Collection<DriverEntity> driversById) {
        this.drivers = driversById;
    }

    @OneToMany(mappedBy = "city")
    public Collection<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItemEntity> orderitemsesById) {
        this.orderItems = orderitemsesById;
    }

    @OneToMany(mappedBy = "city")
    public Collection<VehicleEntity> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Collection<VehicleEntity> vehiclesById) {
        this.vehicles = vehiclesById;
    }
}
