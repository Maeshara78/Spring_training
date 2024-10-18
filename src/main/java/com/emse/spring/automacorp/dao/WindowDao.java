package com.emse.spring.automacorp.dao;

import com.emse.spring.automacorp.model.Room;
import com.emse.spring.automacorp.model.SensorEntity;
import com.emse.spring.automacorp.model.WindowEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WindowDao extends JpaRepository<WindowEntity, Long>,WindowDaoCustom {
    @Modifying
    @Transactional
    @Query("DELETE FROM WindowEntity w WHERE w.room.id = :roomId")
    void deleteByRoomId(@Param("roomId") Long roomId);

    @Modifying
    @Transactional
    @Query("UPDATE WindowEntity w SET w.windowStatus.value = :status WHERE w.room.id = :roomId")
    void updateWindowStatusByRoomId(@Param("roomId") Long roomId, @Param("status") Double status);
}
