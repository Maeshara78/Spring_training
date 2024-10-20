package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.FakeEntityBuilder;
import com.emse.spring.automacorp.model.HeaterEntity;
import com.emse.spring.automacorp.record.Heater;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

public class HeaterMapperTest {

    @Test
    void shouldMapHeater() {
        // Arrange
        HeaterEntity heaterEntity = FakeEntityBuilder.createBuildingEntity(1L, "Building")
                .getRooms()
                .stream()
                .flatMap(room -> room.getHeaters().stream())
                .min(Comparator.comparing(HeaterEntity::getName))
                .orElseThrow(IllegalArgumentException::new);

        // Act
        Heater heater = HeaterMapper.of(heaterEntity);

        // Assert
        Heater expectedHeater = new Heater(
                111L,
                "Heater1Room1Building",
                11L,
                0.0
        );

        Assertions.assertThat(heater).usingRecursiveComparison().isEqualTo(expectedHeater);
    }
}
