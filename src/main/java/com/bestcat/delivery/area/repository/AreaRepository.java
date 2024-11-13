package com.bestcat.delivery.area.repository;

import com.bestcat.delivery.area.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AreaRepository extends JpaRepository<Area, Integer> {

    List<Area> findByCity(String city);

    Area findByAreaId(UUID areaId);

    void deleteByAreaId(UUID areaId);
}
