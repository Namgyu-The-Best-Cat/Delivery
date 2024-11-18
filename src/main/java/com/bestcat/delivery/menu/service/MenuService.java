package com.bestcat.delivery.menu.service;

import com.bestcat.delivery.category.entity.Category;
import com.bestcat.delivery.category.repository.CategoryRepository;
import com.bestcat.delivery.common.exception.RestApiException;
import com.bestcat.delivery.common.type.ErrorCode;
import com.bestcat.delivery.common.util.FileUpload;
import com.bestcat.delivery.menu.dto.MenuRequestDto;
import com.bestcat.delivery.menu.dto.MenuResponseDto;
import com.bestcat.delivery.menu.entity.Menu;
import com.bestcat.delivery.menu.repository.MenuRepository;
import com.bestcat.delivery.store.entity.Store;
import com.bestcat.delivery.store.repository.StoreRepository;
import com.bestcat.delivery.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    private final FileUpload fileUpload;

    public ResponseEntity<List<MenuResponseDto>> getMenus(UUID storeId) {

        List<Menu> menuList = menuRepository.findByStoreStoreIdAndDeletedAtIsNotNull(storeId);

        if (menuList == null || menuList.isEmpty()) throw new RestApiException(ErrorCode.MENU_NOT_FOUND);

        return ResponseEntity.ok().body(menuList.stream().map(MenuResponseDto::from).toList());
    }

    public ResponseEntity<MenuResponseDto> createMenu(MenuRequestDto requestDto, User user) {

        // 이미지 파일 업로드 로직
        String url = "none.png";

        if ( !requestDto.photo().isEmpty() ){
            try {
                url = fileUpload.uploadSingleFile(requestDto.photo());
            } catch (IOException e) {
                throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }

        Menu menu = Menu.builder()
                .store(storeRepository.findById(requestDto.storeId()).orElseThrow( () ->
                        new RestApiException(ErrorCode.STORE_NOT_FOUND)
                ))
                .category(categoryRepository.findById(requestDto.categoryId()).orElseThrow( () ->
                        new RestApiException(ErrorCode.CATEGORY_NOT_FOUND)
                ))
                .name(requestDto.name())
                .price(requestDto.price())
                .photoUrl(url)
                .description(requestDto.description())
                .build();

        menuRepository.save(menu);
        return ResponseEntity.ok(MenuResponseDto.from(menu));
    }


    public ResponseEntity<MenuResponseDto> updateMenu(UUID id, MenuRequestDto requestDto, User user) {

        Menu menu = menuRepository.findById(id).orElseThrow( () ->
            new RestApiException(ErrorCode.MENU_NOT_FOUND)
        );

        if( !menu.getStore().getOwner().getId().equals(user.getId()) ) throw new RestApiException(ErrorCode.INVALID_ROLE);

        Category category = categoryRepository.findById(requestDto.categoryId()).orElseThrow( () ->
                new RestApiException(ErrorCode.CATEGORY_NOT_FOUND)
        );

        // 이미지 파일 업로드 로직
        String url = "none.png";

        if ( !requestDto.photo().isEmpty() ){
            try {
                url = fileUpload.uploadSingleFile(requestDto.photo());
            } catch (IOException e) {
                throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }

        menu.update(
                category,
                requestDto.name(),
                requestDto.price(),
                url,
                requestDto.description()
        );

        menuRepository.save(menu);
        return ResponseEntity.ok(MenuResponseDto.from(menu));
    }

    public ResponseEntity<MenuResponseDto> deleteMenu(UUID id, User user) {

        Menu menu = menuRepository.findById(id).orElseThrow( () ->
                new RestApiException(ErrorCode.MENU_NOT_FOUND)
                );

        if( !menu.getStore().getOwner().getId().equals(user.getId()) ) throw new RestApiException(ErrorCode.INVALID_ROLE);

        menu.delete(user.getId());
        menuRepository.save(menu);
        return ResponseEntity.ok(MenuResponseDto.from(menu));
    }
}
