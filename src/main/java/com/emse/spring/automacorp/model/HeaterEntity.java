package com.emse.spring.automacorp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SP_HEATER")
public class HeaterEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private RoomEntity room;

    @OneToOne
    private SensorEntity heaterStatus;

    public HeaterEntity() {}

    public HeaterEntity(String name, SensorEntity heaterStatus, RoomEntity room) {
        this.name = name;
        this.heaterStatus = heaterStatus;
        this.room = room;
    }

    public Long getId() {
        return id;
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

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public Long getRoomId() {
        return room.getId();
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