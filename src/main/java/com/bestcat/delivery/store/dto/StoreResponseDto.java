package com.bestcat.delivery.store.dto;

import com.bestcat.delivery.store.entity.Store;

import java.util.List;
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
        Integer totalStars,
        List<String> categoryNames
){
    public static StoreResponseDto fromStore(Store store) {

        List<String> categoryNames = store.getStoreCategories().stream()
                .map(storeCategory -> storeCategory.getCategory().getCategoryName())
                .toList();

        return new StoreResponseDto(
                store.getStoreId(),
                store.getStoreName(),
                store.getOwner().getId(),
                store.getStorePhone(),
                store.getArea().getAreaId(),
                store.getDistrict(),
                store.getAddressDetail(),
                store.getTotalReviews(),
                store.getTotalStars(),
                categoryNames
        );
    }

}
