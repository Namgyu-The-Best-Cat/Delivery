package com.bestcat.delivery.area.service;

import com.bestcat.delivery.area.dto.AreaRequestDto;
import com.bestcat.delivery.area.dto.AreaResponseDto;
import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.area.repository.AreaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public List<AreaResponseDto> findAllAreas(){
        List<Area> areas = areaRepository.findAll();
        List<AreaResponseDto> areaResponseDtoList = new ArrayList<>();

        for (Area area : areas) {
            areaResponseDtoList.add(new AreaResponseDto(area));
        }
        return areaResponseDtoList;
    };

    public List<AreaResponseDto> findByCity(String city) {
        List<Area> areas = areaRepository.findByCity(city);
        List<AreaResponseDto> areaResponseDtoList = new ArrayList<>();

        for (Area area : areas) {
            areaResponseDtoList.add(new AreaResponseDto(area));
        }
        return areaResponseDtoList;
    }

    public void addArea(AreaRequestDto areaRequestDto) {

    }
}
