package com.emse.spring.automacorp.api;

import com.emse.spring.automacorp.dto.ApiGouvAdressDto;
import com.emse.spring.automacorp.dto.ApiGouvFeatureDto;
import com.emse.spring.automacorp.dto.ApiGouvResponseDto;
import com.emse.spring.automacorp.search.SearchService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/adress")
@Transactional
public class AdressController {
    private SearchService searchService;

    public AdressController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(path="/address")
    public List<ApiGouvAdressDto> searchAddress(@RequestParam String query) {
        return searchService.searchAddress(query);
    }
}
