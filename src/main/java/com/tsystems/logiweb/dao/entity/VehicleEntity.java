package com.tsystems.logiweb.dao.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by mvolkov on 12.06.2016.
 */
@Entity
@Table(name = "vehicle", schema = "logiweb", catalog = "")
@Access(AccessType.PROPERTY)
public class VehicleEntity {
    private long id;
    private String vin;
    private BigDecimal capacity;
    private int numberOfDrivers;
    private short isAvailable;
    private CityEntity city;
    private OrderEntity order;
    // TODO create collection of Drivers

    public VehicleEntity() {super();}

    @Id@GeneratedValue
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "vin", nullable = false, length = 7)
    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Basic
    @Column(name = "capacity", nullable = false, precision = 1)
    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "nDrivers", nullable = false)
    public int getNumberOfDrivers() {
        return numberOfDrivers;
    }

    public void setNumberOfDrivers(int nDrivers) {
        this.numberOfDrivers = nDrivers;
    }

    @Basic
    @Column(name = "isAvailable", nullable = false)
    public short getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(short isAvailable) {
        this.isAvailable = isAvailable;
    }


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    @OneToOne//(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cityId", referencedColumnName = "id", nullable = false)
    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }
}
