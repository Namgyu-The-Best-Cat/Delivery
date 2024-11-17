package com.bestcat.delivery.menu.service;

import com.bestcat.delivery.category.entity.Category;
import com.bestcat.delivery.common.util.FileUpload;
import com.bestcat.delivery.menu.dto.MenuRequestDto;
import com.bestcat.delivery.menu.dto.MenuResponseDto;
import com.bestcat.delivery.menu.entity.Menu;
import com.bestcat.delivery.menu.repository.MenuRepository;
import com.bestcat.delivery.store.entity.Store;
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
//    private final StoreRepository storeRepository;
//    private final CategoryRepository categoryRepository;

    private final FileUpload fileUpload;

    public ResponseEntity<List<MenuResponseDto>> getMenus(UUID storeId) {

        List<Menu> menuList = menuRepository.findByStoreStoreIdAndDeleteAtIsNotNull(storeId);

        if (menuList == null || menuList.isEmpty()) throw new NullPointerException("메뉴가 존재하지 않습니다.");

        return ResponseEntity.ok().body(menuList.stream().map(MenuResponseDto::from).toList());
    }

    public ResponseEntity<MenuResponseDto> createMenu(MenuRequestDto requestDto, User user) {

        // 이미지 파일 업로드 로직
        String url = "none.png";

        if ( !requestDto.photo().isEmpty() ){
            try {
                url = fileUpload.uploadSingleFile(requestDto.photo());
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 오류 발생 !!");
            }
        }

        // TODO 삭제
        Store store = new Store();
//        store.setStoreId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        Category category = new Category();
//        category.setCategoryId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        //

        Menu menu = Menu.builder()
//                .store(storeRepository.findBy(requestDto.storeId()))
                .store(store)   // TODO 삭제
//                .category(categoryRepository.findById(requestDto.categoryId()))
                .category(category) // TODO 삭제
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
            new NullPointerException("해당하는 메뉴가 존재하지 않습니다.")
        );

//        Category category = categoryRepository.findById(requestDto.categoryId()).orElseThrow( () ->
//                new NullPointerException("해당하는 카테고리가 존재하지 않습니다.")
//        );

        // TODO 삭제
        Category category = new Category();
//        category.setCategoryId(requestDto.categoryId());
        //

        // 이미지 파일 업로드 로직
        String url = "none.png";

        if ( !requestDto.photo().isEmpty() ){
            try {
                url = fileUpload.uploadSingleFile(requestDto.photo());
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 오류 발생 !!");
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
                new NullPointerException("해당하는 메뉴가 존재하지 않습니다.")
                );

        if( !menu.getStore().getOwner().getId().equals(user.getId()) ) throw new IllegalStateException("삭제 권한이 없습니다.");

        menu.delete(user.getId());
        menuRepository.save(menu);
        return ResponseEntity.ok(MenuResponseDto.from(menu));
    }
}
