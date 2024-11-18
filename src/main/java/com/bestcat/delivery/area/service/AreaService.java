package com.bestcat.delivery.area.service;

import com.bestcat.delivery.area.dto.AreaRequestDto;
import com.bestcat.delivery.area.dto.AreaResponseDto;
import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.area.repository.AreaRepository;
import com.bestcat.delivery.common.util.UserDetailsImpl;
import com.bestcat.delivery.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public Page<AreaResponseDto> searchAreas(String city, UUID areaId, String areaName, Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        System.out.println("Searching with parameters:");
        System.out.println("city: " + city);
        System.out.println("areaId: " + areaId);
        System.out.println("areaName: " + areaName);

        Specification<Area> specification = createSpecification(city, areaId, areaName);

        return areaRepository.findAll(specification, pageable)
                .map(AreaResponseDto::fromArea);
    }

    private Specification<Area> createSpecification(String city, UUID areaId, String areaName) {

        Specification<Area> spec = Specification.where(isNotDeleted());

        if (city != null && !city.isEmpty()) {
            spec = spec.and(cityEquals(city));
        }
        if (areaId != null) {
            spec = spec.and(areaIdEquals(areaId));
        }
        if (areaName != null && !areaName.isEmpty()) {
            spec = spec.and(areaNameLike(areaName));
        }

        return spec;
    }
    // deletedAt이 null
    private Specification<Area> isNotDeleted() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }

    // city 일치
    private Specification<Area> cityEquals(String city) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("city"), city);
    }

    // areaId 일치
    private Specification<Area> areaIdEquals(UUID areaId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("areaId"), areaId);
    }

    // areaName 포함
    private Specification<Area> areaNameLike(String areaName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("areaName"), "%" + areaName + "%");
    }

    public void save(AreaRequestDto requestDto) {
        areaRepository.save(requestDto.toEntity());
    }

    @Transactional
    public void updateArea(UUID areaId, @Valid AreaRequestDto areaRequestDto) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new IllegalArgumentException(areaId + " 값을 갖는 area가 없습니다."));

        area.update(areaRequestDto);
    }


    @Transactional
    public void deleteArea(UUID areaId, UUID userId) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 area가 없습니다."));

        area.delete(userId);
    }
}
