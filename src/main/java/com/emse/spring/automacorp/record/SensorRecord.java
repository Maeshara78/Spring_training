package com.emse.spring.automacorp.record;

import com.emse.spring.automacorp.model.SensorEntity;
import com.emse.spring.automacorp.model.SensorType;

import java.util.Objects;

public record SensorRecord(Long id, String name, Double value, SensorType sensorType) {
}
