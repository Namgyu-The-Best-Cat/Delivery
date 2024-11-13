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
    public List<AreaResponseDto> searchAreasByCity(@RequestParam(required = false) String city) {
        List<Area> areas;
        if (city == null) {
            areas = areaService.findAllAreas();
        } else {
            areas = areaService.findByCity(city);
        }

        List<AreaResponseDto> response = areas.stream()
                .map(AreaResponseDto::from)
                .collect(Collectors.toList());

        return response;
    }

    @PostMapping("/areas")
    public void createArea(@Valid @RequestBody AreaRequestDto areaRequestDto) {
        Area newArea = areaRequestDto.toEntity();
        areaService.save(newArea);
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
