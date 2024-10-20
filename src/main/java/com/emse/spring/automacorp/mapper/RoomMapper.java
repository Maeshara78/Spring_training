package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.RoomEntity;
import com.emse.spring.automacorp.record.Room;

public class RoomMapper {
    public static Room of(RoomEntity room) {
        return new Room(
                room.getId(),
                room.getFloor(),
                room.getName(),
                room.getCurrentTemperatureValue(),
                room.getTargetTemperature(),
                room.getWindowsRecords(),
                room.getHeatersRecords(),
                room.getBuildingId() != null ? room.getBuildingId() : null
        );
    }
}
