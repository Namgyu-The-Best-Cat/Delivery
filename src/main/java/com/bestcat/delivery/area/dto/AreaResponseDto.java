package com.bestcat.delivery.area.dto;

import com.bestcat.delivery.area.entity.Area;

import java.util.UUID;

public record AreaResponseDto(
        UUID id,
        String city,
        String areaName
) {

    public static AreaResponseDto fromArea(Area area) {
        return new AreaResponseDto(
                area.getAreaId(),
                area.getCity(),
                area.getAreaName()
        );
    }
}