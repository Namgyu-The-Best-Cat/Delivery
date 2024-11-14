package com.bestcat.delivery.menu.service;

import com.bestcat.delivery.category.entity.Category;
import com.bestcat.delivery.menu.dto.MenuRequestDto;
import com.bestcat.delivery.menu.dto.MenuResponseDto;
import com.bestcat.delivery.menu.entity.Menu;
import com.bestcat.delivery.menu.repository.MenuRepository;
import com.bestcat.delivery.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
//    private final StoreRepository storeRepository;
//    private final CategoryRepository categoryRepository;

    public List<MenuResponseDto> getMenus(String storeId) {

        List<Menu> menuList = menuRepository.findByStoreId(storeId);

        if (menuList == null || menuList.isEmpty()) throw new NullPointerException("메뉴가 존재하지 않습니다.");

        return menuList.stream().map(MenuResponseDto::from).toList();
    }

    public ResponseEntity<MenuResponseDto> createMenu(MenuRequestDto requestDto, User user) {

        // 이미지 파일 업로드 로직

        Menu menu = Menu.builder()
//                .store(storeRepository.findBy(requestDto.storeId()))
//                .category(categoryRepository.findById(requestDto.categoryId()))
                .name(requestDto.name())
                .price(requestDto.price())
                .photoUrl("이미지 업로드 url")
                .description(requestDto.description())
                .build();

        menuRepository.save(menu);
        return ResponseEntity.ok(MenuResponseDto.from(menu));
    }


    public ResponseEntity<MenuResponseDto> updateMenu(UUID id, MenuRequestDto requestDto, User user) {

        Menu menu = menuRepository.findById(id).orElseThrow( () ->
            new NullPointerException("해당하는 메뉴가 존재하지 않습니다.")
        );

//        Category category = categoryRepository.findById(requestDto.categoryId()).orElseThrow( () ->
//                new NullPointerException("해당하는 카테고리가 존재하지 않습니다.")
//        );
        Category category = new Category();


        menu.update(
                category,
                requestDto.name(),
                requestDto.price(),
                requestDto.photoUrl(),
                requestDto.description()
        );

        menuRepository.save(menu);
        return ResponseEntity.ok(MenuResponseDto.from(menu));
    }

    public ResponseEntity<MenuResponseDto> deleteMenu(UUID id, User user) {

        Menu menu = menuRepository.findById(id).orElseThrow( () ->
                new NullPointerException("해당하는 메뉴가 존재하지 않습니다.")
                );

        if( !menu.getStore().getOwner().getId().equals(user.getId()) ) throw new IllegalStateException("삭제 권한이 없습니다.");

        menu.delete(user.getId());
        menuRepository.save(menu);
        return ResponseEntity.ok(MenuResponseDto.from(menu));
    }
}
