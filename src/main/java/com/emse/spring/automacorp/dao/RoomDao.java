package com.emse.spring.automacorp.dao;

import com.emse.spring.automacorp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDao extends JpaRepository<Room, Long> {
}
