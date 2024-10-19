package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.FakeEntityBuilder;
import com.emse.spring.automacorp.model.SensorEntity;
import com.emse.spring.automacorp.model.WindowEntity;
import com.emse.spring.automacorp.record.WindowRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

public class WindowMapperTest {

    @Test
    void shouldMapWindow() {
        // Arrange
        WindowEntity windowEntity = FakeEntityBuilder.createBuildingEntity(1L, "Building")
                .getRooms()
                .stream()
                .flatMap(room -> room.getWindows().stream())
                .min(Comparator.comparing(WindowEntity::getName))
                .orElseThrow(IllegalArgumentException::new);
        // Act
        WindowRecord window = WindowMapper.of(windowEntity);

        // Assert
        WindowRecord expectedWindow = new WindowRecord(
                111L,
                "Window1Room1Building",
                0.0,
                11L
        );

        Assertions.assertThat(window).usingRecursiveAssertion().isEqualTo(expectedWindow);
    }
}
