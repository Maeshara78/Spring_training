package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.RoomEntity;

public class RoomMapper {
    public static RoomEntity of(RoomEntity room) {
        return new RoomEntity(
                room.getId(),
                room.getFloor(),
                room.getName(),
                room.getCurrentTemperature(),
                room.getTargetTemperature(),
                room.getWindows(),
                room.getBuilding()
        );
    }
}
