package com.emse.spring.automacorp.dao;

import com.emse.spring.automacorp.model.RoomEntity;
import com.emse.spring.automacorp.model.WindowEntity;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest // (1)
class WindowDaoTest {
    @Autowired // (2)
    private WindowDao windowDao;

    @Autowired
    private RoomDao roomDao;

    @Test
    public void shouldFindAWindowById() {
        WindowEntity window = windowDao.getReferenceById(-10L); // (3)
        Assertions.assertThat(window.getName()).isEqualTo("Window 1");
        Assertions.assertThat(window.getWindowStatus().getValue()).isEqualTo(1.0);
    }

    @Test
    public void shouldFindRoomsWithOpenWindows() {
        List<WindowEntity> result = windowDao.findRoomsWithOpenWindows(-10L);
        Assertions.assertThat(result)
                .hasSize(1)
                .extracting("id", "name")
                .containsExactly(Tuple.tuple(-10L, "Window 1"));
    }

    @Test
    public void shouldNotFindRoomsWithOpenWindows() {
        List<WindowEntity> result = windowDao.findRoomsWithOpenWindows(-9L);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void shouldFindWindowsWithExistingRoomName() {
        List<WindowEntity> result = windowDao.findAllWindowsByRoomName("Room1");
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id", "name")
                .containsExactly(
                        Tuple.tuple(-10L, "Window 1"),
                        Tuple.tuple(-9L, "Window 2")
                );
    }

    @Test
    public void shouldNotFindWindowsWithWrongRoomName() {
        List<WindowEntity> result = windowDao.findAllWindowsByRoomName("WrongName");
        Assertions.assertThat(result).hasSize(0);
    }

    @Test
    public void shouldDeleteWindowsRoom() {
        RoomEntity room = roomDao.getById(-10L);
        List<Long> roomIds = room.getWindows().stream().map(WindowEntity::getId).collect(Collectors.toList());
        Assertions.assertThat(roomIds).hasSize(2);

        windowDao.deleteByRoomId(-10L);
        List<WindowEntity> result = windowDao.findAllById(roomIds);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void shouldUpdateWindowStatusToOpen() {
        windowDao.updateWindowStatusByRoomId(-10L, 1.0);

        List<WindowEntity> windows = windowDao.findRoomsWithOpenWindows(-10L);
        Assertions.assertThat(windows).hasSize(2);
        Assertions.assertThat(windows).extracting("windowStatus.value").containsOnly(1.0);
    }

    @Test
    public void shouldUpdateWindowStatusToClosed() {
        windowDao.updateWindowStatusByRoomId(-10L, 0.0);

        List<WindowEntity> windows = windowDao.findRoomsWithOpenWindows(-10L);
        Assertions.assertThat(windows).isEmpty();
    }

    @Test
    public void shouldNotUpdateWindowStatusForNonExistentRoom() {
        windowDao.updateWindowStatusByRoomId(-999L, 1.0);

        List<WindowEntity> windows = windowDao.findRoomsWithOpenWindows(-999L);
        Assertions.assertThat(windows).isEmpty();
    }
}