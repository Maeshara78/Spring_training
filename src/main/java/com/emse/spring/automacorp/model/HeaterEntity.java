package com.emse.spring.automacorp.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "SP_HEATER")
public class HeaterEntity {
    @Id
    @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private RoomEntity room;

    @OneToOne
    private SensorEntity heaterStatus;

    public HeaterEntity() {
    }

    public HeaterEntity(Long id, String name, RoomEntity room, SensorEntity heaterStatus) {
        Id = id;
        this.name = name;
        this.room = room;
        this.heaterStatus = heaterStatus;
    }

    public HeaterEntity(String name, SensorEntity heaterStatus, RoomEntity room) {
        this.name = name;
        this.heaterStatus = heaterStatus;
        this.room = room;
    }

    public HeaterEntity(Long id, String name, SensorEntity heaterStatus) {
        Id = id;
        this.name = name;
        this.heaterStatus = heaterStatus;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public SensorEntity getHeaterStatus() {
        return heaterStatus;
    }

    public Double getHeaterStatusValue() {
        return heaterStatus.getValue();
    }

    public void setHeaterStatus(SensorEntity heaterStatus) {
        this.heaterStatus = heaterStatus;
    }
}
