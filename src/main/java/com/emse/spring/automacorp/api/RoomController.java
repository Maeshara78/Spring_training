package com.emse.spring.automacorp.api;

import com.emse.spring.automacorp.dao.RoomDao;
import com.emse.spring.automacorp.dao.WindowDao;
import com.emse.spring.automacorp.dao.SensorDao;
import com.emse.spring.automacorp.dao.HeaterDao;
import com.emse.spring.automacorp.mapper.RoomMapper;
import com.emse.spring.automacorp.mapper.WindowMapper;
import com.emse.spring.automacorp.model.RoomEntity;
import com.emse.spring.automacorp.model.SensorEntity;
import com.emse.spring.automacorp.model.SensorType;
import com.emse.spring.automacorp.model.WindowEntity;
import com.emse.spring.automacorp.record.Room;
import com.emse.spring.automacorp.record.RoomCommand;
import com.emse.spring.automacorp.record.Window;
import com.emse.spring.automacorp.record.WindowCommand;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {
    private final RoomDao roomDao;
    private final SensorDao sensorDao;
    private final HeaterDao heaterDao;
    private final WindowDao windowDao;

    public RoomController(RoomDao roomDao, SensorDao sensorDao, HeaterDao heaterDao, WindowDao windowDao) {
        this.roomDao = roomDao;
        this.sensorDao = sensorDao;
        this.heaterDao = heaterDao;
        this.windowDao = windowDao;
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
    @Transactional
    public ResponseEntity<Room> create(@RequestBody RoomCommand roomCommand) {
        // Check if the room exists
        Optional<RoomEntity> existingRoomOptional = roomDao.findByName(roomCommand.name());
        RoomEntity roomEntity;
        List<WindowEntity> windowEntities = new ArrayList<>();
        
        if (existingRoomOptional.isEmpty()) {
            // Create new Sensor for current temperature
            SensorEntity sensor = new SensorEntity("Sensor" + roomCommand.name(), roomCommand.currentTemperature(), SensorType.TEMPERATURE);
            SensorEntity savedSensor = sensorDao.save(sensor);
            
            // Create RoomEntity
            roomEntity = new RoomEntity(
              roomCommand.name(),
              savedSensor,
              roomCommand.targetTemperature(),
              roomCommand.floor()
            );
            roomEntity = roomDao.save(roomEntity);
        } else {
            // Update existing room
            roomEntity = existingRoomOptional.get();
            roomEntity.setFloor(roomCommand.floor());
            roomEntity.setTargetTemperature(roomCommand.targetTemperature());
            roomEntity.getCurrentTemperature().setValue(roomCommand.currentTemperature());
            
            // Delete existing windows for the room
            windowDao.deleteByRoomId(roomEntity.getId());
        }
        
        // Add new windows to the room
        for (WindowCommand windowCommand : roomCommand.windows()) {
            // Create a sensor entity for window status
            SensorEntity windowSensor = new SensorEntity(
              "Sensor" + windowCommand.name(),
              windowCommand.windowStatus() != null ? windowCommand.windowStatus() : 0.0, // Default to 0.0 if null
              SensorType.STATUS
            );
            SensorEntity savedWindowSensor = sensorDao.save(windowSensor);
            
            // Create and associate the window entity
            WindowEntity windowEntity = new WindowEntity(
              windowCommand.name(),
              savedWindowSensor,
              roomEntity
            );
            roomEntity.getWindows().add(windowEntity);
            windowEntities.add(windowEntity);
        }
        
        // Save windows and associate them with the room
        roomEntity.setWindows(windowEntities);
        roomDao.save(roomEntity);
        
        System.out.println("Room ID: " + roomEntity.getId() + ", Name: " + roomEntity.getName());
        for (WindowEntity window : roomEntity.getWindows()) {
            System.out.println("Window Name: " + window.getName()
              + ", Room ID: " + window.getRoomId()
              + ", Sensor Name: " + (window.getWindowStatus() != null ? window.getWindowStatus().getName() : "null")
              + ", Sensor Value: " + (window.getWindowStatus() != null ? window.getWindowStatus().getValue() : "null"));
        }
        // Return the created or updated room
        return ResponseEntity.ok(RoomMapper.of(roomEntity));
    }
    
    
    
    @PutMapping("/{id}")
    public ResponseEntity<Room> update(@PathVariable Long id, @RequestBody RoomCommand room) {
        RoomEntity existingRoom = roomDao.findById(id)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
        
        existingRoom.setName(room.name());
        existingRoom.setFloor(room.floor());
        existingRoom.setTargetTemperature(room.targetTemperature());
        if (room.currentTemperature() != null) {
            existingRoom.getCurrentTemperature().setValue(room.currentTemperature());
        }
        
        RoomEntity updatedRoom = roomDao.save(existingRoom);
        return ResponseEntity.ok(RoomMapper.of(updatedRoom));
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        RoomEntity room = roomDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        heaterDao.deleteAll(room.getHeaters());
        windowDao.deleteAll(room.getWindows());
        roomDao.deleteById(id);
    }
    
    @GetMapping(path = "{id}/windows")
    public List<Window> getWindows(@PathVariable Long id) {
        System.out.println("Finding windows for roomId: " + id);
        RoomEntity room = roomDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        List<Window> windows = room.getWindows().stream().map(WindowMapper::of).collect(Collectors.toList());
        System.out.println("Found windows: " + windows);
        return windows;
    }

    @PutMapping(path = "{id}/openWindows")
    public ResponseEntity<Room> openWindows(@PathVariable Long id) {
        RoomEntity room = roomDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        room.getWindows().forEach(window -> window.getWindowStatus().setValue(1.0));
        RoomEntity updatedRoom = roomDao.save(room);
        return ResponseEntity.ok(RoomMapper.of(updatedRoom));
    }

    @PutMapping(path = "{id}/closeWindows")
    public ResponseEntity<Room> closeWindows(@PathVariable Long id) {
        RoomEntity room = roomDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        room.getWindows().forEach(window -> window.getWindowStatus().setValue(0.0));
        RoomEntity updatedRoom = roomDao.save(room);
        return ResponseEntity.ok(RoomMapper.of(updatedRoom));
    }


}
