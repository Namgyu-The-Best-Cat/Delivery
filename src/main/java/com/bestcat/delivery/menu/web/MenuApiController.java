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
    public List<MenuResponseDto> getMenus(@PathVariable String storeId) {
        return menuService.getMenus(storeId);
    }

    @PostMapping("/menus")
    public ResponseEntity<MenuResponseDto> createMenu(@RequestBody MenuRequestDto requestDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return menuService.createMenu(requestDto, userDetails.getUser());
    }

    @PutMapping("/menus/{id}")
    public ResponseEntity<MenuResponseDto> updateMenu(@PathVariable UUID id
            , @RequestBody MenuRequestDto requestDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return menuService.updateMenu(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/menus/{id}")
    public ResponseEntity<MenuResponseDto> deleteMenu(@PathVariable UUID id
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return menuService.deleteMenu(id, userDetails.getUser());
    }
}
