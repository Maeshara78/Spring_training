package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.BuildingEntity;
import com.emse.spring.automacorp.model.FakeEntityBuilder;
import com.emse.spring.automacorp.record.Building;
import com.emse.spring.automacorp.record.Heater;
import com.emse.spring.automacorp.record.Room;
import com.emse.spring.automacorp.record.Window;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BuildingMapperTest {

    @Test
    void shouldMapBuilding() {
        // Arrange
        BuildingEntity buildingEntity = FakeEntityBuilder.createBuildingEntity(1L, "Building");

        // Act
        Building building = BuildingMapper.of(buildingEntity);

        // Assert
        Building expectedBuilding = new Building(
                1L,
                "Building",
                23.2,
                List.of(
                    new Room(
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
                    ),
                    new Room(
                        12L,
                        1,
                        "Room2Building",
                        23.2,
                        26.4,
                        List.of(
                            new Window(
                                121L,
                                "Window1Room2Building",
                                0.0,
                                12L
                            ),
                            new Window(
                                122L,
                                "Window2Room2Building",
                                0.0,
                                12L
                            )
                        ),
                        List.of(
                            new Heater(
                                121L,
                                "Heater1Room2Building",
                                12L,
                                0.0
                            ),
                            new Heater(
                                122L,
                                "Heater2Room2Building",
                                12L,
                                0.0
                            )
                        ),
                        1L
                    )
                )
        );

        Assertions.assertThat(building).usingRecursiveComparison().isEqualTo(expectedBuilding);
    }
}
