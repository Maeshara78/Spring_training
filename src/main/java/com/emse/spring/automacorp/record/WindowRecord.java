package com.emse.spring.automacorp.record;

import com.emse.spring.automacorp.model.SensorEntity;

public record WindowRecord(Long id, String name, SensorEntity windowStatus, Long roomId) {
}
