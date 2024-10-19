package com.emse.spring.automacorp.dao;

import com.emse.spring.automacorp.model.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingDao extends JpaRepository<BuildingEntity, Long> {
}
