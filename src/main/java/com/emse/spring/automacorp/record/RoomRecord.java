package com.emse.spring.automacorp.record;

import com.emse.spring.automacorp.model.SensorEntity;
import com.emse.spring.automacorp.model.WindowEntity;

import java.util.Set;

public record RoomRecord(Long Id, Integer floor, String name, SensorEntity currentTemperature, Double targetTemperature, Set<WindowEntity> windows) {
}
