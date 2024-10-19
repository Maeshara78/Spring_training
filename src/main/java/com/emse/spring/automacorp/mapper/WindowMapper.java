package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.WindowEntity;
import com.emse.spring.automacorp.record.WindowRecord;

public class WindowMapper {
    public static WindowRecord of(WindowEntity window) {
        return new WindowRecord(
                window.getId(),
                window.getName(),
                window.getWindowsSensorValue(),
                window.getRoomId()
        );
    }
}
