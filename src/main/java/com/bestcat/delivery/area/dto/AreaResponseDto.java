package com.bestcat.delivery.area.dto;

import com.bestcat.delivery.area.entity.Area;

public class AreaResponseDto {

    private String city;
    private String areaName;

    public AreaResponseDto(Area area) {
        this.city = area.getCity();
        this.areaName = area.getAreaName();
    }
}
