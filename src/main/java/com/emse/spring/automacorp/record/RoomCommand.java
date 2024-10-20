package com.emse.spring.automacorp.record;

public record RoomCommand(Integer floor, String name, Double currentTemperature, Double targetTemperature) {
}
