package com.bestcat.delivery.store.web;

import com.bestcat.delivery.common.util.UserDetailsImpl;
import com.bestcat.delivery.store.dto.StoreRequestDto;
import com.bestcat.delivery.store.dto.StoreResponseDto;
import com.bestcat.delivery.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @Secured({"ROLE_MASTER", "ROLE_MANAGER"})
    @PostMapping("/stores")
    public ResponseEntity<String> createStore(@Valid @RequestBody StoreRequestDto storeRequestDto) {
        storeService.save(storeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Store가 성공적으로 추가되었습니다: " + storeRequestDto);
    }

    @Secured({"ROLE_MASTER", "ROLE_MANAGER", "ROLE_OWNER", "ROLE_CUSTOMER"})
    @GetMapping("/stores")
    public ResponseEntity<Page<StoreResponseDto>> getStores(
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) UUID storeId,
            @RequestParam(required = false) String areaName,
            @RequestParam(required = false) UUID areaId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<StoreResponseDto> stores = storeService.searchStores(
                storeName, storeId, areaName, areaId, page, size);
        return ResponseEntity.ok(stores);
    }

    @Secured({"ROLE_MASTER", "ROLE_MANAGER", "ROLE_OWNER"})
    @PutMapping("/stores/{storeId}")
    public ResponseEntity<String> updateStore(@PathVariable UUID storeId,
                                              @Valid @RequestBody StoreRequestDto storeRequestDto) {
        storeService.updateStore(storeId, storeRequestDto);
        return ResponseEntity.ok("Store가 성공적으로 업데이트되었습니다: " + storeId);
    }

    @Secured({"ROLE_MASTER", "ROLE_MANAGER"})
    @DeleteMapping("/stores/{storeId}")
    public ResponseEntity<String> deleteStore(@PathVariable UUID storeId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        storeService.deleteStore(storeId, userDetails.getUserId());
        return ResponseEntity.ok("Store가 성공적으로 삭제되었습니다: " + storeId);
    }
}
