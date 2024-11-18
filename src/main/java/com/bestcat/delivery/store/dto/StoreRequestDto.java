package com.bestcat.delivery.store.dto;

import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.category.entity.Category;
import com.bestcat.delivery.store.entity.Store;
import com.bestcat.delivery.store.entity.StoreCategory;
import com.bestcat.delivery.user.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record StoreRequestDto (
        String storeName,
        UUID ownerId,
        List<UUID> categoryIds,
        String storePhone,
        UUID areaId,
        String district,
        String addressDetail,
        Integer totalReviews,
        Integer totalStars
){
    public Store toEntity(User owner, Area area) {
        if (owner == null) {
            throw new IllegalArgumentException("사장님이 없습니다.");
        }
        if (area == null) {
            throw new IllegalArgumentException("가게 위치에 해당하는 지역이 없습니다.");
        }


        return Store.builder()
                .storeName(storeName)
                .owner(owner)
                .storePhone(storePhone)
                .area(area)
                .district(district)
                .addressDetail(addressDetail)
                .totalReviews(totalReviews)
                .totalStars(totalStars)
                .build();
    }

}
