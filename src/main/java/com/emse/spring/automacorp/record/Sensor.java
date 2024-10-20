package com.emse.spring.automacorp.record;

import com.emse.spring.automacorp.model.SensorType;

public record Sensor(Long id, String name, Double value, SensorType sensorType) {
}
