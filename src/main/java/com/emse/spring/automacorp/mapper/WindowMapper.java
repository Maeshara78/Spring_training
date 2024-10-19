package com.emse.spring.automacorp.mapper;

import com.emse.spring.automacorp.model.WindowEntity;

public class WindowMapper {
    public static WindowEntity of(WindowEntity window) {
        return new WindowEntity(
                window.getId(),
                window.getName(),
                window.getWindowStatus()
        );
    }
}
