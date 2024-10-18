package com.emse.spring.automacorp.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "SP_ROOM")
public class Room {
    @Id
    @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private SensorEntity currentTemperature;

    @Column
    private Double targetTemperature;

    @OneToMany(mappedBy = "room")
    private Set<WindowEntity> windows = Set.of();

    public Room() {
    }

    public Room(Long id, Integer floor, String name, SensorEntity currentTemperature, Double targetTemperature, Set<WindowEntity> windows) {
        Id = id;
        this.floor = floor;
        this.name = name;
        this.currentTemperature = currentTemperature;
        this.targetTemperature = targetTemperature;
        this.windows = windows;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorEntity getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(SensorEntity currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public Double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public Set<WindowEntity> getWindows() {
        return windows;
    }

    public void setWindows(Set<WindowEntity> windows) {
        this.windows = windows;
    }
}
