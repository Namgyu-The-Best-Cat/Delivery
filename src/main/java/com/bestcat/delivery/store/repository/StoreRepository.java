package com.bestcat.delivery.store.repository;

import com.bestcat.delivery.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface StoreRepository extends JpaRepository <Store, UUID>, JpaSpecificationExecutor<Store> {
}
