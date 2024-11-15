package com.bestcat.delivery.area.repository;

import com.bestcat.delivery.area.entity.Area;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, UUID> {
    Optional<Area> findByCityAndAreaNameAndDeletedAtIsNull(String city, String s);
}
