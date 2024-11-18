package com.bestcat.delivery.menu.web;

import com.bestcat.delivery.common.util.UserDetailsImpl;
import com.bestcat.delivery.menu.dto.MenuRequestDto;
import com.bestcat.delivery.menu.dto.MenuResponseDto;
import com.bestcat.delivery.menu.service.MenuService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuApiController {

    private final MenuService menuService;

    @GetMapping("/stores/{storeId}/menus")
    public ResponseEntity<List<MenuResponseDto>> getMenus(@PathVariable String storeId) {
        return menuService.getMenus(UUID.fromString(storeId));
    }

    @PostMapping(value = "/menus", consumes = "multipart/form-data")
    public ResponseEntity<MenuResponseDto> createMenu(@ModelAttribute MenuRequestDto requestDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return menuService.createMenu(requestDto, userDetails.getUser());
    }

    @PutMapping(value = "/menus/{id}", consumes = "multipart/form-data")
    public ResponseEntity<MenuResponseDto> updateMenu(@PathVariable String id
            , @ModelAttribute MenuRequestDto requestDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return menuService.updateMenu(UUID.fromString(id), requestDto, userDetails.getUser());
    }

    @DeleteMapping("/menus/{id}")
    public ResponseEntity<MenuResponseDto> deleteMenu(@PathVariable String id
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return menuService.deleteMenu(UUID.fromString(id), userDetails.getUser());
    }
}
