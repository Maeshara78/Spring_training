package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.record.Heater;
import com.emse.spring.automacorp.record.Room;
import com.emse.spring.automacorp.record.Window;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.util.Comparator;
import java.util.List;
import com.emse.spring.automacorp.model.*;

class RoomMapperTest {

    @Test
    void shouldMapRoom() {
        // Arrange
        RoomEntity roomEntity = FakeEntityBuilder.createBuildingEntity(1L, "Building")
                .getRooms()
                .stream()
                .min(Comparator.comparing(RoomEntity::getName))
                .orElseThrow(IllegalArgumentException::new);

        // Act
        Room room = RoomMapper.of(roomEntity);

        // Assert
        Room expectedRoom = new Room(
                11L,
                1,
                "Room1Building",
                23.2,
                26.4,
                List.of(
                        new Window(
                                111L,
                                "Window1Room1Building",
                                0.0,
                                11L
                        ),
                        new Window(
                                112L,
                                "Window2Room1Building",
                                0.0,
                                11L
                        )
                ),
                List.of(
                        new Heater(
                                111L,
                                "Heater1Room1Building",
                                11L,
                                0.0
                        ),
                        new Heater(
                                112L,
                                "Heater2Room1Building",
                                11L,
                                0.0
                        )
                ),
                1L
        );
        Assertions.assertThat(room).usingRecursiveAssertion().isEqualTo(expectedRoom);
    }
}
