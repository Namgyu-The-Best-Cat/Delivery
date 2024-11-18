package com.bestcat.delivery.user.dto;

import com.bestcat.delivery.user.entity.DeliveryAddress;

public record DeliveryAddressResponseDto(
        String city,
        String areaName,
        String detailAddress
) {
    public DeliveryAddressResponseDto(DeliveryAddress deliveryAddress) {
        this(deliveryAddress.getArea().getCity(),
                deliveryAddress.getArea().getAreaName(),
                deliveryAddress.getDetailedAddress());
    }
}

