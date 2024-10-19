package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.*;
import org.junit.jupiter.api.Test;
import com.emse.spring.automacorp.record.SensorRecord;

import java.util.Comparator;

public class SensorMapperTest {

    @Test
    void shouldMapSensor() {
        // Arrange
        SensorEntity sensorEntity = FakeEntityBuilder.createBuildingEntity(1L, "Building")
                .getRooms()
                .stream()
                .flatMap(room -> room.getWindows().stream())
                .map(WindowEntity::getWindowStatus)
                .min(Comparator.comparing(SensorEntity::getName))
                .orElseThrow(IllegalArgumentException::new);

        // Act
        SensorRecord sensor = SensorMapper.of(sensorEntity);

        // Assert
        SensorRecord expectedSensor = new SensorRecord(
                11L,
                "WindowStatus1Room1Building",
                0.0,
                SensorType.STATUS
        );
    }
}
