package com.tsystems.logiweb.dao.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by mvolkov on 12.06.2016.
 */
@Entity
@Table(name = "distance", schema = "logiweb", catalog = "")
@Access(AccessType.PROPERTY)
public class DistanceEntity {

    private long id;
    private CityEntity city1;
    private CityEntity city2;
    private BigDecimal distance;


    public DistanceEntity(){super();}


    @Id@GeneratedValue
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "distance", nullable = false, precision = 1)
    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    @OneToOne
    @JoinColumn(name = "cityId1", referencedColumnName = "id", nullable = false)
    public CityEntity getCity1() {
        return city1;
    }

    public void setCity1(CityEntity cityByCityId1) {
        this.city1 = cityByCityId1;
    }

    @OneToOne
    @JoinColumn(name = "cityId2", referencedColumnName = "id", nullable = false)
    public CityEntity getCity2() {
        return city2;
    }

    public void setCity2(CityEntity cityByCityId2) {
        this.city2 = cityByCityId2;
    }
}
