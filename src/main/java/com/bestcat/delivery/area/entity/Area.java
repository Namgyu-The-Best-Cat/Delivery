package com.bestcat.delivery.area.entity;

import com.bestcat.delivery.area.dto.AreaRequestDto;
import com.bestcat.delivery.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name="p_service_area")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Area extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "area_id", updatable = false, nullable = false)
    private UUID areaId;

    @Column(name = "city")
    private String city;

    @Column(name = "area_name")
    private String areaName;


    public void update(@Valid AreaRequestDto areaRequestDto) {
        if (areaRequestDto.city() != null) {
            this.city = areaRequestDto.city();
        }
        if (areaRequestDto.areaName() != null) {
            this.areaName = areaRequestDto.areaName();
        }
    }
}