package com.emse.spring.automacorp.search;

import com.emse.spring.automacorp.dto.ApiGouvAdressDto;
import com.emse.spring.automacorp.dto.ApiGouvResponseDto;
import com.emse.spring.automacorp.dto.ApiGouvFeatureDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final RestTemplate restTemplate;

    public SearchService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.rootUri("https://api-adresse.data.gouv.fr").build();
    }

    public List<ApiGouvAdressDto> searchAddress(String query) {
        String url = UriComponentsBuilder.fromUriString("/search")
                .queryParam("q", query)
                .queryParam("limit", 15)
                .build()
                .toUriString();

        ApiGouvResponseDto response = this.restTemplate.getForObject(url, ApiGouvResponseDto.class);

        if (response == null || response.features() == null) {
            return List.of();
        }

        return response.features().stream()
                .map(ApiGouvFeatureDto::properties)
                .collect(Collectors.toList());
    }
}
