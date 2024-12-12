package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.WindowEntity;
import com.emse.spring.automacorp.record.Window;

public class WindowMapper {
    public static Window of(WindowEntity window) {
        return new Window(
          window.getId(),
          window.getName(),
          window.getWindowsSensorValue(),
          window.getRoom() != null ? window.getRoom().getId() : null
        );
    }
}

