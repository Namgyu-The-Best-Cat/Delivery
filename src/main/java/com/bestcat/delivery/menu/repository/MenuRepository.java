package com.bestcat.delivery.menu.repository;

import com.bestcat.delivery.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID> {
    List<Menu> findByStoreStoreIdAndDeletedAtIsNotNull(UUID storeId);
}
