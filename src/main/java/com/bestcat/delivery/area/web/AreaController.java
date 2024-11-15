package com.bestcat.delivery.area.web;
import com.bestcat.delivery.area.dto.AreaRequestDto;
import com.bestcat.delivery.area.dto.AreaResponseDto;
import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.area.repository.AreaRepository;
import com.bestcat.delivery.area.service.AreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AreaController {

    private final AreaService areaService;

    @GetMapping("/areas")
    public List<AreaResponseDto> searchAreasByCity(@RequestParam(required = false) String city,@RequestParam(required = false) UUID areaID, @RequestParam(required = false) String areaName) {
        return areaService.searchAreas(city,areaID,areaName);


    }

    @PostMapping("/areas")
    public void createArea(@Valid @RequestBody AreaRequestDto areaRequestDto) {
        areaService.save(areaRequestDto);
    }

    @PutMapping("/areas/{areaId}")
    public void updateArea(@PathVariable UUID areaId, @Valid @RequestBody AreaRequestDto areaRequestDto) {
        areaService.updateArea(areaId, areaRequestDto);
    }

    @DeleteMapping("/areas/{areaId}")
    public void deleteArea(@PathVariable UUID areaId) {
        areaService.deleteArea(areaId);
    }

}