package com.emse.spring.automacorp.model;

import com.emse.spring.automacorp.mapper.HeaterMapper;
import com.emse.spring.automacorp.mapper.WindowMapper;
import com.emse.spring.automacorp.record.Heater;
import com.emse.spring.automacorp.record.Window;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "SP_ROOM")
public class RoomEntity {
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
    
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WindowEntity> windows = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<HeaterEntity> heaters;

    @ManyToOne
    private BuildingEntity building;

    public RoomEntity() {
    }

    public RoomEntity(Long id, Integer floor, String name, SensorEntity currentTemperature, Double targetTemperature, List<WindowEntity> windows, List<HeaterEntity> heaters, BuildingEntity building) {
        Id = id;
        this.name = name;
        this.floor = floor;
        this.currentTemperature = currentTemperature;
        this.targetTemperature = targetTemperature;
        this.windows = windows;
        this.building = building;
        this.heaters = heaters;
    }

    public RoomEntity(Long id, Integer floor, String name, SensorEntity currentTemperature, Double targetTemperature, List<WindowEntity> windows, List<HeaterEntity> heaters) {
        Id = id;
        this.name = name;
        this.floor = floor;
        this.currentTemperature = currentTemperature;
        this.targetTemperature = targetTemperature;
        this.windows = windows;
        this.heaters = heaters;
    }

    public RoomEntity(Long id, String name, Integer floor, SensorEntity currentTemperature, Double targetTemperature) {
        Id = id;
        this.name = name;
        this.floor = floor;
        this.currentTemperature = currentTemperature;
        this.targetTemperature = targetTemperature;
    }

    public RoomEntity(String name, SensorEntity currentTemperature, Integer floor) {
        this.name = name;
        this.currentTemperature = currentTemperature;
        this.floor = floor;
    }

    public RoomEntity(String name, SensorEntity currentTemperature, Double targetTemperature, Integer floor) {
        this.name = name;
        this.currentTemperature = currentTemperature;
        this.targetTemperature = targetTemperature;
        this.floor = floor;
    }

    public BuildingEntity getBuilding() {
        return building;
    }

    public void setBuilding(BuildingEntity building) {
        this.building = building;
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

    public Double getCurrentTemperatureValue() {
        return currentTemperature.getValue();
    }

    public SensorEntity getCurrentTemperature() {
        return currentTemperature;
    }

    public Double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public List<HeaterEntity> getHeaters() {
        return heaters;
    }

    public List<Heater> getHeatersRecords() {
        return heaters.stream().map(HeaterMapper::of).collect(Collectors.toList());
    }

    public void setHeaters(List<HeaterEntity> heaters) {
        this.heaters = heaters;
    }

    public List<WindowEntity> getWindows() {
        return windows;
    }

    public List<Window> getWindowsRecords() {
        return windows.stream().map(WindowMapper::of).collect(Collectors.toList());
    }

    public void setWindows(List<WindowEntity> windows) {
        this.windows = windows;
    }

    public void setCurrentTemperature(SensorEntity currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public Long getBuildingId() {
        return building.getId();
    }
}
