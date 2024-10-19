package com.emse.spring.automacorp.model;

import jakarta.persistence.*;

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

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public SensorEntity getHeaterStatus() {
        return heaterStatus;
    }

    public void setHeaterStatus(SensorEntity heaterStatus) {
        this.heaterStatus = heaterStatus;
    }
}
