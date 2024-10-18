package com.emse.spring.automacorp.model;

import com.emse.spring.automacorp.model.SensorEntity;
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
    private Room room;

    public WindowEntity() {
    }

    public WindowEntity(String name, SensorEntity sensor, Room room) {
        this.windowStatus = sensor;
        this.name = name;
        this.room = room;
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

    public void setWindowStatus(SensorEntity windowStatus) {
        this.windowStatus = windowStatus;
    }
}