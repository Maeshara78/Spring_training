package com.emse.spring.automacorp.record;

import java.util.List;

public record Room(Long Id, Integer floor, String name, Double currentTemperature, Double targetTemperature, List<Window> windows, List<Heater> heaters, Long BuildingID) {
}
