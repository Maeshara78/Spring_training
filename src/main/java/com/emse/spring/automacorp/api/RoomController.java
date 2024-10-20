package com.emse.spring.automacorp.api;

import com.emse.spring.automacorp.dao.RoomDao;
import com.emse.spring.automacorp.dao.WindowDao;
import com.emse.spring.automacorp.dao.SensorDao;
import com.emse.spring.automacorp.dao.HeaterDao;
import com.emse.spring.automacorp.mapper.RoomMapper;
import com.emse.spring.automacorp.model.RoomEntity;
import com.emse.spring.automacorp.model.SensorEntity;
import com.emse.spring.automacorp.model.SensorType;
import com.emse.spring.automacorp.record.Room;
import com.emse.spring.automacorp.record.RoomCommand;
import org.springframework.http.HttpHeaders;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {
    private final RoomDao roomDao;
    private final SensorDao sensorDao;

    public RoomController(RoomDao roomDao, SensorDao sensorDao) {
        this.roomDao = roomDao;
        this.sensorDao = sensorDao;
    }

    @GetMapping
    public List<Room> findAll() {
        return roomDao.findAll()
                .stream()
                .map(RoomMapper::of)
                .sorted(Comparator.comparing(Room::name))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public Room findById(@PathVariable Long id) {
        return roomDao.findById(id).map(RoomMapper::of).orElse(null);
    }

    @PostMapping
    public ResponseEntity<Room> create(@RequestBody RoomCommand room) {
        if (roomDao.findByName(room.name()).isEmpty()) {
            SensorEntity sensor = new SensorEntity("Sensor" + room.name(), room.currentTemperature(), SensorType.TEMPERATURE);
            SensorEntity savedSensor = sensorDao.save(sensor);
            RoomEntity entity = new RoomEntity(room.name(), savedSensor, room.targetTemperature(), room.floor());
            RoomEntity savedRoom = roomDao.save(entity);
            return ResponseEntity.ok(RoomMapper.of(savedRoom));
        } else {
            RoomEntity existingRoom = roomDao.findByName(room.name()).get();
            existingRoom.setFloor(room.floor());
            existingRoom.setTargetTemperature(room.targetTemperature());
            existingRoom.getCurrentTemperature().setValue(room.currentTemperature());
            RoomEntity updatedRoom = roomDao.save(existingRoom);
            return ResponseEntity.ok(RoomMapper.of(updatedRoom));
        }
    }
}
