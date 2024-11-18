package com.bestcat.delivery.store.dto;

import com.bestcat.delivery.store.entity.Store;

import java.util.List;
import java.util.UUID;

public record StoreResponseDto(
        UUID storeId,
        String storeName,
        UUID ownerId,
        String storePhone,
        String city,
        String areaName,
        String district,
        String addressDetail,
        Integer totalReviews,
        Integer totalStars,
        double stars,
        List<String> categoryNames
){
    public static StoreResponseDto fromStore(Store store) {

        List<String> categoryNames = store.getStoreCategories().stream()
                .map(storeCategory -> storeCategory.getCategory().getCategoryName())
                .toList();
        double stars;
        if(store.getTotalReviews() != 0) {
             stars = store.getTotalStars() / store.getTotalReviews();
        }else{
            stars =0;
        }
        return new StoreResponseDto(
                store.getStoreId(),
                store.getStoreName(),
                store.getOwner().getId(),
                store.getStorePhone(),
                store.getArea().getCity(),
                store.getArea().getAreaName(),
                store.getDistrict(),
                store.getAddressDetail(),
                store.getTotalReviews(),
                store.getTotalStars(),
                stars,
                categoryNames
        );
    }

}
