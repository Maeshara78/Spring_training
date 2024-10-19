package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.BuildingEntity;
import com.emse.spring.automacorp.model.FakeEntityBuilder;
import com.emse.spring.automacorp.record.BuildingRecord;
import com.emse.spring.automacorp.record.HeaterRecord;
import com.emse.spring.automacorp.record.RoomRecord;
import com.emse.spring.automacorp.record.WindowRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BuildingMapperTest {

    @Test
    void shouldMapBuilding() {
        // Arrange
        BuildingEntity buildingEntity = FakeEntityBuilder.createBuildingEntity(1L, "Building");

        // Act
        BuildingRecord building = BuildingMapper.of(buildingEntity);

        // Assert
        BuildingRecord expectedBuilding = new BuildingRecord(
                1L,
                "Building",
                23.2,
                List.of(
                    new RoomRecord(
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
                    ),
                    new RoomRecord(
                        12L,
                        1,
                        "Room2Building",
                        23.2,
                        26.4,
                        List.of(
                            new WindowRecord(
                                121L,
                                "Window1Room2Building",
                                0.0,
                                12L
                            ),
                            new WindowRecord(
                                122L,
                                "Window2Room2Building",
                                0.0,
                                12L
                            )
                        ),
                        List.of(
                            new HeaterRecord(
                                121L,
                                "Heater1Room2Building",
                                12L,
                                0.0
                            ),
                            new HeaterRecord(
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
