package com.emse.spring.automacorp.model;

import com.emse.spring.automacorp.mapper.RoomMapper;
import com.emse.spring.automacorp.record.Room;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "SP_BUILDING")
public class BuildingEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @OneToOne
    private SensorEntity outsideTemperature;

    @OneToMany(mappedBy = "building")
    private List<RoomEntity> rooms = List.of();

    public BuildingEntity() {}

    public BuildingEntity(Long id, String name, SensorEntity outsideTemperature) {
        this.id = id;
        this.name = name;
        this.outsideTemperature = outsideTemperature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SensorEntity getOutsideTemperature() {
        return outsideTemperature;
    }

    public Double getOutsideTemperatureValue() {
        return outsideTemperature.getValue();
    }

    public void setOutsideTemperature(SensorEntity outsideTemperature) {
        this.outsideTemperature = outsideTemperature;
    }

    public List<RoomEntity> getRooms() {
        return rooms;
    }

    public List<Room> getRoomsRecord() {
        return rooms.stream().map(RoomMapper::of).toList();
    }

    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }
}