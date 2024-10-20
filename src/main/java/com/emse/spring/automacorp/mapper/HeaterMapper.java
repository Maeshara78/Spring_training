package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.HeaterEntity;
import com.emse.spring.automacorp.record.Heater;

public class HeaterMapper {
    public static Heater of(HeaterEntity heater) {
        return new Heater(
                heater.getId(),
                heater.getName(),
                heater.getRoomId(),
                heater.getHeaterStatusValue()
        );
    }
}
