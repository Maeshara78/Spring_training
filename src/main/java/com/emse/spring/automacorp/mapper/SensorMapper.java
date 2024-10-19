package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.SensorEntity;

public class SensorMapper {
    public static SensorEntity of(SensorEntity sensor) {
        return new SensorEntity(
                sensor.getId(),
                sensor.getName(),
                sensor.getValue(),
                sensor.getSensorType()
        );
    }
}

