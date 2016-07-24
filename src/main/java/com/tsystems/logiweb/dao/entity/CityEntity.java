package com.tsystems.logiweb.dao.entity;

import javax.persistence.*;
import java.util.*;

/**
 * Created by mvolkov on 12.06.2016.
 */
@Entity
@Table(name = "city", schema = "logiweb", catalog = "")
@Access(AccessType.PROPERTY)
public class CityEntity {
    private long id;
    private String name;

    Set<CityEntity> neighbors = new HashSet<>();

    @Transient
    public Set<CityEntity> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Set<CityEntity> neighbors) {
        this.neighbors = neighbors;
    }


    public CityEntity() {
        super();
    }

    public CityEntity(String city) {
        this.name = city;
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
    @Column(name = "city", nullable = false, length = 50, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String city) {
        this.name = city;
    }
}
