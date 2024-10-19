package com.emse.spring.automacorp.record;

import java.util.List;

public record BuildingRecord(Long Id, String name, Double outsideTemperature, List<RoomRecord> rooms) {
}
