package com.tsystems.logiweb.dao.entity;

import javax.persistence.*;

/**
 * Created by mvolkov on 12.06.2016.
 */
@Entity
@Table(name = "orderitems", schema = "jschool2", catalog = "")
@Access(AccessType.PROPERTY)
public class OrderItemEntity {
    private long id;
    private int itemNumber;
    private CityEntity city;
    private OrderEntity order;

    @Id@GeneratedValue
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


    @ManyToOne
    @JoinColumn(name = "cityId", referencedColumnName = "id", nullable = false)
    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id", nullable = false)
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
