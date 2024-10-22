package com.emse.spring.automacorp.api;

import com.emse.spring.automacorp.dao.RoomDao;
import com.emse.spring.automacorp.dao.SensorDao;
import com.emse.spring.automacorp.dao.WindowDao;
import com.emse.spring.automacorp.model.*;
import com.emse.spring.automacorp.record.WindowCommand;
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

@WebMvcTest(WindowController.class)
class WindowControllerTest {
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

    private final RoomEntity room = FakeEntityBuilder.createRoomEntity(1L, "Room1", null);

    private final WindowEntity window = FakeEntityBuilder.createWindowEntity(1L, "Window1", room);

    private final WindowCommand expectedWindow = new WindowCommand(window.getName(), window.getWindowsSensorValue(), window.getRoomId());

    @Test
    void shouldFindAll() throws Exception {
        Mockito.when(windowDao.findAll()).thenReturn(List.of(
                FakeEntityBuilder.createWindowEntity(1L, "Window1", room),
                FakeEntityBuilder.createWindowEntity(2L, "Window2", room)
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/windows").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("[*].name")
                                .value(Matchers.containsInAnyOrder("Window1", "Window2"))
                );
    }

    @Test
    void shouldReturnNullWhenFindByUnknownId() throws Exception {
        Mockito.when(windowDao.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/windows/999").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void shouldFindById() throws Exception {
        Mockito.when(windowDao.findById(999L)).thenReturn(Optional.of(window));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/windows/999").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Window1"));
    }

    @Test
    void shouldNotUpdateUnknownEntity() throws Exception {
        String json = objectMapper.writeValueAsString(expectedWindow);

        Mockito.when(windowDao.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/api/windows/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldUpdate() throws Exception {
        String json = objectMapper.writeValueAsString(expectedWindow);

        Mockito.when(roomDao.findById(1L)).thenReturn(Optional.of(room));
        Mockito.when(windowDao.findById(1L)).thenReturn(Optional.of(window));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/windows/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Window1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    void shouldCreate() throws Exception {
        SensorEntity sensor = FakeEntityBuilder.createSensorEntity(1L, "SensorWindow1", SensorType.TEMPERATURE, 0.0);

        String json = objectMapper.writeValueAsString(expectedWindow);

        Mockito.when(roomDao.findById(1L)).thenReturn(Optional.of(room));
        Mockito.when(sensorDao.save(Mockito.any())).thenReturn(sensor);
        Mockito.when(windowDao.save(Mockito.any())).thenReturn(window);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/windows")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Window1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    void shouldDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/windows/999"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
