package com.bestcat.delivery.area.web;

import com.bestcat.delivery.area.dto.AreaRequestDto;
import com.bestcat.delivery.area.dto.AreaResponseDto;
import com.bestcat.delivery.area.service.AreaService;
import jakarta.persistence.Column;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping("/areas")
    public List<AreaResponseDto> getAllAreas() {
        return areaService.findAllAreas();
    }

    @GetMapping("/areas/{city}")
    public List<AreaResponseDto> getAreasByCity(@PathVariable String city) {
        return areaService.findByCity(city);
    }
}
