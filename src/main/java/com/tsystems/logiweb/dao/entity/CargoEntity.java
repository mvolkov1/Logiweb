package com.tsystems.logiweb.dao.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by mvolkov on 12.06.2016.
 */
@Entity
@Table(name = "cargo", schema = "logiweb", catalog = "")
@Access(AccessType.PROPERTY)
public class CargoEntity {
    private long id;
    private String uid;
    private String title;
    private BigDecimal mass;
    private String status;
    private OrderItemEntity itemStart;
    private OrderItemEntity itemEnd;
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
    @Column(name = "uid", nullable = false, length = 12)
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 200)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "mass", nullable = false, precision = 1)
    public BigDecimal getMass() {
        return mass;
    }

    public void setMass(BigDecimal mass) {
        this.mass = mass;
    }


    @Basic
    @Column(name = "status1", nullable = false, length = 10)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @OneToOne
    @JoinColumn(name = "itemStartId", referencedColumnName = "id", nullable = false)
    public OrderItemEntity getItemStart() {
        return itemStart;
    }

    public void setItemStart(OrderItemEntity itemStart) {
        this.itemStart = itemStart;
    }

    @OneToOne
    @JoinColumn(name = "itemEndId", referencedColumnName = "id", nullable = false)
    public OrderItemEntity getItemEnd() {
        return itemEnd;
    }

    public void setItemEnd(OrderItemEntity itemEnd) {
        this.itemEnd = itemEnd;
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
