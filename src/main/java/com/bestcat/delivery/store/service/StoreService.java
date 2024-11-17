package com.bestcat.delivery.store.service;

import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.area.repository.AreaRepository;
import com.bestcat.delivery.category.entity.Category;
import com.bestcat.delivery.category.repository.CategoryRepository;
import com.bestcat.delivery.store.dto.StoreRequestDto;
import com.bestcat.delivery.store.dto.StoreResponseDto;
import com.bestcat.delivery.store.entity.Store;
import com.bestcat.delivery.store.entity.StoreCategory;
import com.bestcat.delivery.store.repository.StoreRepository;
import com.bestcat.delivery.user.entity.User;
import com.bestcat.delivery.user.repository.UserRepository;
import jakarta.persistence.criteria.Join;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;
    private final CategoryRepository categoryRepository;

    public StoreService(StoreRepository storeRepository, UserRepository userRepository, AreaRepository areaRepository, CategoryRepository categoryRepository) {
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
        this.categoryRepository = categoryRepository;
    }

    public void save(@Valid StoreRequestDto storeRequestDto) {
        User owner = userRepository.findById(storeRequestDto.ownerId())
                .orElseThrow(()-> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        Area area = areaRepository.findById(storeRequestDto.areaId())
                .orElseThrow(()-> new IllegalArgumentException("해당하는 지역이 없습니다."));

        Store store = storeRequestDto.toEntity(owner, area);

        Set<StoreCategory> storeCategories = new HashSet<>();

        for (UUID categoryId : storeRequestDto.categoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 Category가 없습니다."));
            StoreCategory storeCategory = new StoreCategory(store, category);
            storeCategories.add(storeCategory);
        }

        // Store에 StoreCategory 설정
        store.setStoreCategories(storeCategories);

        // Store 저장
        storeRepository.save(store); // 이때 Store와 관련된 StoreCategory도 함께 저장됨
    }


    public Page<StoreResponseDto> searchStores(String storeName, UUID storeId, String areaName, UUID areaId, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Specification<Store> specification = createSpecification(storeName, storeId, areaName, areaId);

        return storeRepository.findAll(specification, pageable)
                .map(StoreResponseDto::fromStore);
    }

    private Specification<Store> createSpecification(String storeName, UUID storeId, String areaName, UUID areaId) {
        return Specification.where(storeNameLike(storeName))
                .and(storeIdEquals(storeId))
                .and(areaNameEquals(areaName))
                .and(areaIdEquals(areaId));
    }

    private Specification<Store> areaIdEquals(UUID areaId) {
        if (areaId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            Join<Store, Area> areaJoin = root.join("area");
            return criteriaBuilder.equal(areaJoin.get("areaId"), areaId);
        };
    }

    private Specification<Store> areaNameEquals(String areaName) {
        if (areaName == null || areaName.isEmpty()) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            Join<Store, Area> areaJoin = root.join("area");
            return criteriaBuilder.equal(areaJoin.get("name"), areaName);
        };
    }

    private Specification<Store> storeIdEquals(UUID storeId) {
        if (storeId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("storeId"), storeId);
    }

    private Specification<Store> storeNameLike(String storeName) {
        if (storeName == null || storeName.isEmpty()) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("storeName"), "%" + storeName + "%");
    }

    @Transactional
    public void updateStore(UUID storeId, @Valid StoreRequestDto storeRequestDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException(storeId + "값을 갖는 Store가 없습니다."));

    }

//    @Transactional
//    public void deleteStore(UUID storeId, @Valid StoreRequestDto storeRequestDto) {
//        Store store = storeRepository.findById(storeId)
//                .orElseThrow(() -> new IllegalArgumentException(storeId + "에 해당하는 Store가 없습니다."));
//    }
}
