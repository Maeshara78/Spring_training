package com.emse.spring.automacorp.dto;

import java.util.List;

public record ApiGouvResponseDto(String version,
                                 String query,
                                 Integer limit,
                                 List<ApiGouvFeatureDto> features){
}
