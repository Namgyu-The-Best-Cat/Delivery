package com.bestcat.delivery.area.repository;

import com.bestcat.delivery.area.entity.Area;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, UUID> {

    List<Area> findByCity(String city);

    void deleteByAreaId(UUID areaId);

    List<Area> findByCityAndAreaId(String city, UUID areaId);

    List<Area> findByCityAndAreaIdAndAreaName(String city, UUID areaId, String areaName);

    List<Area> findByCityAndAreaName(String city, String areaName);

    List<Area> findByAreaIdAndAreaName(UUID areaId, String areaName);

    List<Area> findByAreaName(String areaName);

    Optional<Area> findByCityAndAreaNameAndDeletedAtIsNull(String city, String s);
}
