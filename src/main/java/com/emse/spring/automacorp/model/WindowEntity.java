package com.emse.spring.automacorp.model;

import com.emse.spring.automacorp.mapper.SensorMapper;
import com.emse.spring.automacorp.record.Sensor;
import jakarta.persistence.*;

@Entity
@Table(name = "SP_WINDOW")
public class WindowEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private SensorEntity windowStatus;

    @ManyToOne
    private RoomEntity room;

    public WindowEntity() {
    }

    public WindowEntity(Long id, String name, SensorEntity windowStatus, RoomEntity room) {
        this.id = id;
        this.name = name;
        this.windowStatus = windowStatus;
        this.room = room;
    }

    public WindowEntity(String name, SensorEntity sensorEntity, RoomEntity roomEntity) {
        this.name = name;
        this.windowStatus = sensorEntity;
        this.room = roomEntity;
    }

    public WindowEntity(Long id, String name, SensorEntity windowStatus) {
        this.id = id;
        this.name = name;
        this.windowStatus = windowStatus;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorEntity getWindowStatus() {
        return windowStatus;
    }

    public Sensor getWindowStatusRecord() {
        return SensorMapper.of(windowStatus);
    }

    public Double getWindowsSensorValue() {
        return windowStatus.getValue();
    }

    public void setWindowStatus(SensorEntity windowStatus) {
        this.windowStatus = windowStatus;
    }

    public void setValue(Double value) {
        this.windowStatus.setValue(value);
    }

    public RoomEntity getRoom() {
        return room;
    }

    public Long getRoomId() {
        return room.getId();
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }
}