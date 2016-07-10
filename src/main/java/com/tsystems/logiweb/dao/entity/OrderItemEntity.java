package com.tsystems.logiweb.dao.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by mvolkov on 12.06.2016.
 */
@Entity
@Table(name = "orderitem", schema = "logiweb", catalog = "")
@Access(AccessType.PROPERTY)
public class OrderItemEntity {
    private long id;
    private int itemNumber;
    private CityEntity city;
    private OrderEntity order;
    private BigDecimal distance;
    private BigDecimal fullDistance;
    private int time = 0;
    private int fullTime = 0;

    @Transient
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Transient
    public int getFullTime() {
        return fullTime;
    }

    public void setFullTime(int fullTime) {
        this.fullTime = fullTime;
    }

    @Transient
    public BigDecimal getFullDistance() {
        return fullDistance;
    }

    public void setFullDistance(BigDecimal fullDistance) {
        this.fullDistance = fullDistance;
    }

    @Transient
    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
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
    @Column(name = "itemNumber", nullable = false)
    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    @OneToOne(optional = false)
    @JoinColumn(name = "cityId", referencedColumnName = "id", nullable = false)
    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "orderId", referencedColumnName = "id", nullable = false)
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
