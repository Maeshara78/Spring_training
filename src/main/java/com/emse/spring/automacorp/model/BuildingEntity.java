package com.emse.spring.automacorp.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "SP_BUILDING")
public class BuildingEntity {
    @Id
    @GeneratedValue
    private Long Id;

    @OneToOne
    private SensorEntity outsideTemperature;

    @OneToMany(mappedBy = "building")
    private Set<RoomEntity> rooms = Set.of();

    public BuildingEntity() {
    }

    public BuildingEntity(Long id, SensorEntity outsideTemperature, Set<RoomEntity> rooms) {
        Id = id;
        this.outsideTemperature = outsideTemperature;
        this.rooms = rooms;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public SensorEntity getOutsideTemperature() {
        return outsideTemperature;
    }

    public void setOutsideTemperature(SensorEntity outsideTemperature) {
        this.outsideTemperature = outsideTemperature;
    }

    public Set<RoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(Set<RoomEntity> rooms) {
        this.rooms = rooms;
    }
}
