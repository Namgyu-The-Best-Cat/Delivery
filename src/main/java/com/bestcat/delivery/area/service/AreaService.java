package com.bestcat.delivery.area.service;

import com.bestcat.delivery.area.dto.AreaRequestDto;
import com.bestcat.delivery.area.dto.AreaResponseDto;
import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.area.repository.AreaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }


    public List<Area> findAllAreas() {
        return areaRepository.findAll();
    }

    public List<Area> findByCity(String city) {
        return areaRepository.findByCity(city);
    }

    public void save(Area newArea) {
        areaRepository.save(newArea);
    }

    @Transactional
    public void updateArea(UUID areaId, @Valid AreaRequestDto areaRequestDto) {
        Area updateArea = areaRepository.findByAreaId(areaId);
        updateArea.update(areaRequestDto);

    }

    public void deleteArea(UUID areaId) {
        areaRepository.deleteByAreaId(areaId);
    }
}
