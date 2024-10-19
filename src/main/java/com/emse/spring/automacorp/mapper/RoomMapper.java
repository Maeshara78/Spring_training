package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.RoomEntity;
import com.emse.spring.automacorp.record.RoomRecord;

public class RoomMapper {
    public static RoomRecord of(RoomEntity room) {
        return new RoomRecord(
                room.getId(),
                room.getFloor(),
                room.getName(),
                room.getCurrentTemperatureValue(),
                room.getTargetTemperature(),
                room.getWindowsRecords(),
                room.getHeatersRecords(),
                room.getBuildingId()
        );
    }
}
