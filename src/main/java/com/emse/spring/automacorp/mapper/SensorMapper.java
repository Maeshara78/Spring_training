package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.SensorEntity;
import com.emse.spring.automacorp.record.SensorRecord;

public class SensorMapper {
    public static SensorRecord of(SensorEntity sensor) {
        return new SensorRecord(
                sensor.getId(),
                sensor.getName(),
                sensor.getValue(),
                sensor.getSensorType()
        );
    }
}

