package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.HeaterEntity;
import com.emse.spring.automacorp.record.HeaterRecord;

public class HeaterMapper {
    public static HeaterRecord of(HeaterEntity heater) {
        return new HeaterRecord(
                heater.getId(),
                heater.getName(),
                heater.getRoomId(),
                heater.getHeaterStatusValue()
        );
    }
}
