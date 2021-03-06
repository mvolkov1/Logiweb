package com.tsystems.logiweb.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="startTime")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }



    private VehicleEntity vehicle;
    private Collection<OrderItemEntity> items = new ArrayList<>();
    private Collection<CargoEntity> cargos = new ArrayList<>();
    private Collection<DriverEntity> drivers = new ArrayList<>();


    public OrderEntity() {
        super();
    }

    @Id
    @GeneratedValue
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    public Collection<CargoEntity> getCargos() {
        return cargos;
    }

    public void setCargos(Collection<CargoEntity> cargos) {
        this.cargos = cargos;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    public Collection<OrderItemEntity> getItems() {
        return items;
    }

    public void setItems(Collection<OrderItemEntity> orderItems) {
        this.items = orderItems;
    }

    @Transient
    public String getVehicleVin() {
        String vehicleVin = null;
        if (vehicle != null) {
            vehicleVin = vehicle.getVin();
            if (vehicleVin != null && !vehicleVin.isEmpty()) {
                return vehicleVin;
            }
        }
        return vehicleVin;
    }
}
