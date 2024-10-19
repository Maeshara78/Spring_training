package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.BuildingEntity;
import com.emse.spring.automacorp.record.BuildingRecord;

public class BuildingMapper {
    public static BuildingRecord of(BuildingEntity building) {
        return new BuildingRecord(
                building.getId(),
                building.getName(),
                building.getOutsideTemperatureValue(),
                building.getRoomsRecord()
        );
    }
}
