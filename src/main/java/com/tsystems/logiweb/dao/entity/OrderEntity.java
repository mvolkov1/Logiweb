package com.tsystems.logiweb.dao.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by mvolkov on 12.06.2016.
 */
@Entity
@Table(name = "order1", schema = "logiweb", catalog = "")
@Access(AccessType.PROPERTY)
public class OrderEntity {
    private long id;
    private String uid;
    private short isCompleted;
    private int numberOfItems;

    private VehicleEntity vehicle;
    private Collection<OrderItemEntity> orderItems;
    private Collection<CargoEntity> cargos;
    private Collection<DriverEntity> drivers;


    public OrderEntity(){super();}

    @Id@GeneratedValue
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uid", nullable = false, length = 12)
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "isCompleted", nullable = false)
    public short getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(short isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Basic
    @Column(name = "nItems", nullable = false)
    public int getNumberOfItems() {return numberOfItems;}

    public void setNumberOfItems(int numberOfItems) {this.numberOfItems = numberOfItems;}


    @OneToMany(mappedBy = "order")
    public Collection<CargoEntity> getCargos() {
        return cargos;
    }

    public void setCargos(Collection<CargoEntity> cargos) {
        this.cargos = cargos;
    }

    @OneToMany(mappedBy = "order")
    public Collection<DriverEntity> getDrivers() {
        return drivers;
    }

    public void setDrivers(Collection<DriverEntity> drivers) {
        this.drivers = drivers;
    }

    //    @OneToOne
//    @JoinColumn(name = "vehicleId", referencedColumnName = "id")
    @OneToOne(mappedBy = "order")
    public VehicleEntity getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
    }

    @OneToMany(mappedBy = "order")
    public Collection<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }
}
