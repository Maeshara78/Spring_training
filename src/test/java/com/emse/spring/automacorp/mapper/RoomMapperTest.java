package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.record.HeaterRecord;
import com.emse.spring.automacorp.record.RoomRecord;
import com.emse.spring.automacorp.record.SensorRecord;
import com.emse.spring.automacorp.record.WindowRecord;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.util.Comparator;
import java.util.List;
import com.emse.spring.automacorp.model.*;
import com.emse.spring.automacorp.mapper.RoomMapper;

import static com.emse.spring.automacorp.model.SensorType.TEMPERATURE;

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
        RoomRecord room = RoomMapper.of(roomEntity);

        // Assert
        RoomRecord expectedRoom = new RoomRecord(
                11L,
                1,
                "Room1Building",
                23.2,
                26.4,
                List.of(
                        new WindowRecord(
                                111L,
                                "Window1Room1Building",
                                0.0,
                                11L
                        ),
                        new WindowRecord(
                                112L,
                                "Window2Room1Building",
                                0.0,
                                11L
                        )
                ),
                List.of(
                        new HeaterRecord(
                                111L,
                                "Heater1Room1Building",
                                11L,
                                0.0
                        ),
                        new HeaterRecord(
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
