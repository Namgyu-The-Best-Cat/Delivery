package com.bestcat.delivery.menu.repository;

import com.bestcat.delivery.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
    List<Menu> findByStoreId(String storeId);
}
