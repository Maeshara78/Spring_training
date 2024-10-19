package com.emse.spring.automacorp.record;

import com.emse.spring.automacorp.model.BuildingEntity;
import com.emse.spring.automacorp.model.HeaterEntity;
import com.emse.spring.automacorp.model.SensorEntity;
import com.emse.spring.automacorp.model.WindowEntity;

import java.util.List;
import java.util.Set;

public record RoomRecord(Long Id, Integer floor, String name, Double currentTemperature, Double targetTemperature, List<WindowRecord> windows, List<HeaterRecord> heaters, Long BuildingID) {
}
