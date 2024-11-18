package com.bestcat.delivery.area.dto;

import com.bestcat.delivery.area.entity.Area;
import org.antlr.v4.runtime.misc.NotNull;

public record AreaRequestDto (
        @NotNull
        String city,

        @NotNull
        String areaName
) {
    public Area toEntity() {
        return Area.builder()
                .city(city)
                .areaName(areaName)
                .build();
    }
}
