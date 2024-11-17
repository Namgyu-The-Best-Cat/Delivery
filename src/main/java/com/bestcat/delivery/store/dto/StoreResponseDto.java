package com.bestcat.delivery.store.dto;

import com.bestcat.delivery.store.entity.Store;

import java.util.UUID;

public record StoreResponseDto(
        UUID storeId,
        String storeName,
        UUID ownerId,
        String storePhone,
        UUID areaId,
        String district,
        String addressDetail,
        Integer totalReviews,
        Integer totalStars
){
    public static StoreResponseDto fromStore(Store store) {
        return new StoreResponseDto(
                store.getStoreId(),
                store.getStoreName(),
                store.getOwner().getId(),
                store.getStorePhone(),
                store.getArea().getAreaId(),
                store.getDistrict(),
                store.getAddressDetail(),
                store.getTotalReviews(),
                store.getTotalStars()
        );
    }

}
