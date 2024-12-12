package com.emse.spring.automacorp.record;

import java.util.List;

public record RoomCommand(
  Integer floor,
  String name,
  Double currentTemperature,
  Double targetTemperature,
  List<WindowCommand> windows
) {}
