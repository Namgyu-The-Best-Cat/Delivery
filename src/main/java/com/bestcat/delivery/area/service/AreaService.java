package com.bestcat.delivery.area.service;

import com.bestcat.delivery.area.dto.AreaRequestDto;
import com.bestcat.delivery.area.dto.AreaResponseDto;
import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.area.repository.AreaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public List<AreaResponseDto> searchAreas(String city, UUID areaId, String areaName, Integer page, Integer size) {
        List<Area> areas;

        if (city != null && areaId != null && areaName != null) {
            areas = areaRepository.findByCityAndAreaIdAndAreaName(city, areaId, areaName);
        } else if (city != null && areaId != null) {
            areas = areaRepository.findByCityAndAreaId(city, areaId);
        } else if (city != null && areaName != null) {
            areas = areaRepository.findByCityAndAreaName(city, areaName);
        } else if (areaId != null && areaName != null) {
            areas = areaRepository.findByAreaIdAndAreaName(areaId, areaName);
        } else if (city != null) {
            areas = areaRepository.findByCity(city);
        } else if (areaId != null) {
            Optional<Area> areaOptional = areaRepository.findById(areaId);
            areas = areaOptional.map(Collections::singletonList)
                    .orElseGet(() -> {
                        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);
                        return areaRepository.findAll(pageable).getContent();
                    });

        } else if (areaName != null) {
            areas = areaRepository.findByAreaName(areaName);
        } else {
            Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);
            areas = areaRepository.findAll(pageable).getContent();
        }

        return areas.stream()
                .map(AreaResponseDto::from)
                .collect(Collectors.toList());
    }

    public void save(AreaRequestDto requestDto) {
        areaRepository.save(requestDto.toEntity());
    }

    @Transactional
    public void updateArea(UUID areaId, @Valid AreaRequestDto areaRequestDto) {
        Optional<Area> optionalArea = areaRepository.findById(areaId);
        if (optionalArea.isPresent()) {
            Area area = optionalArea.get();
            area.update(areaRequestDto);
        } else {
            throw new EntityNotFoundException( areaId + " 값을 갖는 area가 없습니다.");  // 값이 없을 경우 예외 처리
        }

    }

    public void deleteArea(UUID areaId) {
        areaRepository.deleteByAreaId(areaId);
    }
}
