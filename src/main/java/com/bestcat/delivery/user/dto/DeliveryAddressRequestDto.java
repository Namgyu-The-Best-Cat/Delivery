package com.bestcat.delivery.user.dto;

import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.user.entity.DeliveryAddress;
import com.bestcat.delivery.user.entity.User;

public record DeliveryAddressRequestDto(
        String city,
        String areaName,
        String detailAddress
) {
    public DeliveryAddress toEntity(User user, Area area) {
        return DeliveryAddress.builder()
                .user(user)
                .area(area)
                .detailedAddress(detailAddress)
                .build();
    }

}
