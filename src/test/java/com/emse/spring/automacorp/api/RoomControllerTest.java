package com.emse.spring.automacorp.api;

import com.emse.spring.automacorp.dao.*;
import com.emse.spring.automacorp.model.*;
import com.emse.spring.automacorp.record.RoomCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WindowDao windowDao;

    @MockBean
    private RoomDao roomDao;

    @MockBean
    private SensorDao sensorDao;

    @MockBean
    private HeaterDao heaterDao;

    @MockBean
    private BuildingDao buildingDao;

    private final BuildingEntity building = FakeEntityBuilder.createBuildingEntity(1L, "Building1");

    private final RoomEntity room = FakeEntityBuilder.createRoomEntity(1L, "Room1", building);

    private final WindowEntity window = FakeEntityBuilder.createWindowEntity(1L, "Window1", room);

    private final SensorEntity sensor = FakeEntityBuilder.createSensorEntity(1L, "Sensor1", SensorType.TEMPERATURE, 23.2);

    private final RoomCommand expectedRoom = new RoomCommand(room.getFloor(), room.getName(), room.getCurrentTemperatureValue(), room.getTargetTemperature());

    @Test
    void shouldFindAll() throws Exception {
        Mockito.when(roomDao.findAll()).thenReturn(List.of(
            FakeEntityBuilder.createRoomEntity(1L, "Room1", building),
            FakeEntityBuilder.createRoomEntity(2L, "Room2", building)
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("[*].name")
                    .value(Matchers.containsInAnyOrder("Room1", "Room2"))
            );
    }

    @Test
    void shouldReturnNullWhenFindByUnknownId() throws Exception {
        Mockito.when(roomDao.findById(999L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms/999").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void shouldFindById() throws Exception {
        Mockito.when(roomDao.findById(999L)).thenReturn(java.util.Optional.of(room));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms/999").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("name").value("Room1"));
    }

    @Test
    void shouldUpdate() throws Exception {
        String json = objectMapper.writeValueAsString(expectedRoom);

        Mockito.when(roomDao.findByName("Room1")).thenReturn(Optional.of(room));
        Mockito.when(roomDao.save(Mockito.any(RoomEntity.class))).thenReturn(room);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/rooms")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Room1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Id").value("1"));
    }

    @Test
    void shouldCreate() throws Exception {
        String json = objectMapper.writeValueAsString(expectedRoom);

        Mockito.when(roomDao.findByName("Room1")).thenReturn(Optional.of(room));
        Mockito.when(sensorDao.save(Mockito.any(SensorEntity.class))).thenReturn(sensor);
        Mockito.when(roomDao.save(Mockito.any(RoomEntity.class))).thenReturn(room);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/rooms")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Room1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Id").value("1"));
    }

    @Test
    void shouldDelete() throws Exception {
        Mockito.when(roomDao.findById(999L)).thenReturn(Optional.of(room));
        Mockito.doNothing().when(heaterDao).deleteAll(room.getHeaters());
        Mockito.doNothing().when(windowDao).deleteAll(room.getWindows());
        Mockito.doNothing().when(roomDao).deleteById(999L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/rooms/999"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(roomDao, Mockito.times(1)).findById(999L);
        Mockito.verify(heaterDao, Mockito.times(1)).deleteAll(room.getHeaters());
        Mockito.verify(windowDao, Mockito.times(1)).deleteAll(room.getWindows());
        Mockito.verify(roomDao, Mockito.times(1)).deleteById(999L);
    }

    @Test
    void shouldOpenWindows() throws Exception {
        // Arrange: Mock the room with windows and set initial status to 0.0
        WindowEntity window1 = FakeEntityBuilder.createWindowEntity(1L, "Window1", room);
        window1.getWindowStatus().setValue(0.0);
        WindowEntity window2 = FakeEntityBuilder.createWindowEntity(2L, "Window2", room);
        window2.getWindowStatus().setValue(0.0);

        room.setWindows(List.of(window1, window2));

        Mockito.when(roomDao.findById(1L)).thenReturn(Optional.of(room));
        Mockito.when(roomDao.save(Mockito.any(RoomEntity.class))).thenReturn(room);

        // Act: Perform the PUT request to open windows
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/rooms/1/openWindows")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Room1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.windows[0].windowStatus").value(1.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.windows[1].windowStatus").value(1.0));

        // Assert: Verify that the save method was called to persist the updated room state
        Mockito.verify(roomDao, Mockito.times(1)).save(Mockito.any(RoomEntity.class));
    }

    @Test
    void shouldCloseWindows() throws Exception {
        // Arrange: Mock the room with windows and set their initial status to 1.0 (open)
        WindowEntity window1 = FakeEntityBuilder.createWindowEntity(1L, "Window1", room);
        window1.getWindowStatus().setValue(1.0);
        WindowEntity window2 = FakeEntityBuilder.createWindowEntity(2L, "Window2", room);
        window2.getWindowStatus().setValue(1.0);

        room.setWindows(List.of(window1, window2));

        Mockito.when(roomDao.findById(1L)).thenReturn(Optional.of(room));
        Mockito.when(roomDao.save(Mockito.any(RoomEntity.class))).thenReturn(room);

        // Act: Perform the PUT request to close the windows
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/rooms/1/closeWindows")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Room1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.windows[0].windowStatus").value(0.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.windows[1].windowStatus").value(0.0));

        // Assert: Verify that the save method was called to persist the updated room state
        Mockito.verify(roomDao, Mockito.times(1)).save(Mockito.any(RoomEntity.class));
    }
}
