package com.emse.spring.automacorp.dao;

import com.emse.spring.automacorp.model.HeaterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeaterDAO extends JpaRepository<HeaterEntity, Long> {
}
