package com.emse.spring.automacorp.record;

import java.util.List;

public record Building(Long Id, String name, Double outsideTemperature, List<Room> rooms) {
}
