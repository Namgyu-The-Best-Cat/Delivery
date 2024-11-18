package com.bestcat.delivery.area.repository;

import com.bestcat.delivery.area.entity.Area;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.query.criteria.JpaSearchedCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, UUID>, JpaSpecificationExecutor<Area> {

    Optional<Area> findByCityAndAreaNameAndDeletedAtIsNull(String city, String s);
}
