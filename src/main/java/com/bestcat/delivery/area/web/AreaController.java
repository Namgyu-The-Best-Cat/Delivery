package com.bestcat.delivery.area.web;
import com.bestcat.delivery.area.dto.AreaRequestDto;
import com.bestcat.delivery.area.dto.AreaResponseDto;
import com.bestcat.delivery.area.service.AreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AreaController {

    private final AreaService areaService;

    @GetMapping("/areas")
    public ResponseEntity<Page<AreaResponseDto>> searchAreasByCity(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) UUID areaID,
            @RequestParam(required = false) String areaName,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<AreaResponseDto> areas = areaService.searchAreas(city, areaID, areaName, page, size);
        return ResponseEntity.ok(areas);
    }

    @Secured({"ROLE_MASTER"})
    @PostMapping("/areas")
    public ResponseEntity<String> createArea(@Valid @RequestBody AreaRequestDto areaRequestDto) {
        areaService.save(areaRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Area가 추가되었습니다. "+areaRequestDto);
    }

    @Secured({"ROLE_MASTER"})
    @PutMapping("/areas/{areaId}")
    public ResponseEntity<String> updateArea(@PathVariable UUID areaId, @Valid @RequestBody AreaRequestDto areaRequestDto) {
        areaService.updateArea(areaId, areaRequestDto);
        return ResponseEntity.ok("Area가 업데이트되었습니다.");
    }

    @Secured({"ROLE_MASTER"})
    @DeleteMapping("/areas/{areaId}")
    public ResponseEntity<String> deleteArea(@PathVariable UUID areaId) {
        areaService.deleteArea(areaId);
        return ResponseEntity.ok("Area가 삭제되었습니다.");
    }

}
