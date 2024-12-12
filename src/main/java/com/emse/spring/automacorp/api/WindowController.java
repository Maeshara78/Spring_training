package com.emse.spring.automacorp.api;

import com.emse.spring.automacorp.dao.RoomDao;
import com.emse.spring.automacorp.dao.WindowDao;
import com.emse.spring.automacorp.dao.SensorDao;
import com.emse.spring.automacorp.mapper.RoomMapper;
import com.emse.spring.automacorp.mapper.SensorMapper;
import com.emse.spring.automacorp.mapper.WindowMapper;
import com.emse.spring.automacorp.model.RoomEntity;
import com.emse.spring.automacorp.model.SensorEntity;
import com.emse.spring.automacorp.model.SensorType;
import com.emse.spring.automacorp.model.WindowEntity;
import com.emse.spring.automacorp.record.Room;
import com.emse.spring.automacorp.record.Sensor;
import com.emse.spring.automacorp.record.Window;
import com.emse.spring.automacorp.record.WindowCommand;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/windows")
@Transactional
public class WindowController {
    private final WindowDao windowDao;
    private final RoomDao roomDao;
    private final SensorDao sensorDao;

    public WindowController(WindowDao windowDao, RoomDao roomDao, SensorDao sensorDao) {
        this.windowDao = windowDao;
        this.roomDao = roomDao;
        this.sensorDao = sensorDao;
    }

    @GetMapping
    public List<Window> findAll() {
        return windowDao.findAll()
                .stream()
                .map(WindowMapper::of)
                .sorted(Comparator.comparing(Window::name))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public Window findById(@PathVariable Long id) {
        return windowDao.findById(id).map(WindowMapper::of).orElse(null); // (7)
    }

    @PostMapping // (8)
    public ResponseEntity<Window> create(@RequestBody WindowCommand window) { // (9)
        SensorEntity sensor = new SensorEntity("Sensor" + window.name(), window.windowStatus(), SensorType.STATUS);
        SensorEntity savedSensor = sensorDao.save(sensor);
        RoomEntity room = roomDao.findById(window.roomId()).orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        WindowEntity entity = new WindowEntity(window.name(), savedSensor, room);
        WindowEntity saved = windowDao.save(entity);
        return ResponseEntity.ok(WindowMapper.of(saved));
    }

    @PutMapping(path = "/{id}") // (10)
    public ResponseEntity<Window> update(@PathVariable Long id, @RequestBody WindowCommand window) {
        WindowEntity entity = windowDao.findById(id).orElse(null);
        if (entity == null) {
            return ResponseEntity.badRequest().build();
        }
        entity.setValue(window.windowStatus());
        entity.setName(window.name());
        RoomEntity room = roomDao.findById(window.roomId()).orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        entity.setRoom(room);
        // (11)
        return ResponseEntity.ok(WindowMapper.of(entity));
    }
    
    @PutMapping(path = "{id}/openWindow")
    public ResponseEntity<Window> openWindow(@PathVariable Long id) {
        WindowEntity window = windowDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid window ID"));
        window.setValue(1.0);
        WindowEntity updatedWindow = windowDao.save(window);
        return ResponseEntity.ok(WindowMapper.of(updatedWindow));
    }
    
    @PutMapping(path = "{id}/closeWindow")
    public ResponseEntity<Window> closeWindow(@PathVariable Long id) {
        WindowEntity window = windowDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid window ID"));
        window.setValue(0.0);
        WindowEntity updatedWindow = windowDao.save(window);
        return ResponseEntity.ok(WindowMapper.of(updatedWindow));
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        windowDao.deleteById(id);
    }
}
