package com.bestcat.delivery.store.web;

import com.bestcat.delivery.store.dto.StoreRequestDto;
import com.bestcat.delivery.store.dto.StoreResponseDto;
import com.bestcat.delivery.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping("/stores")
    public void createStore(@Valid @RequestBody StoreRequestDto storeRequestDto) {
        storeService.save(storeRequestDto);
    }

    @GetMapping("/stores")
    public Page<StoreResponseDto> getStores(
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) UUID storeId,
            @RequestParam(required = false) String areaName,
            @RequestParam(required = false) UUID areaId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return storeService.searchStores(
                storeName, storeId, areaName, areaId, page, size);
    }

    @PutMapping("/stores/{storeId}")
    public void updateStore(@PathVariable UUID storeId, @Valid @RequestBody StoreRequestDto storeRequestDto) {
        storeService.updateStore(storeId, storeRequestDto);
    }

//    @PatchMapping("/stores/{storeId}")
//    public void patchStore(@PathVariable UUID storeId, @Valid @RequestBody StoreRequestDto storeRequestDto) {
//        storeService.deleteStore(storeId, storeRequestDto);
//    }
}
