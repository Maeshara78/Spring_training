package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.BuildingEntity;
import com.emse.spring.automacorp.record.Building;

public class BuildingMapper {
    public static Building of(BuildingEntity building) {
        return new Building(
                building.getId(),
                building.getName(),
                building.getOutsideTemperatureValue(),
                building.getRoomsRecord()
        );
    }
}
