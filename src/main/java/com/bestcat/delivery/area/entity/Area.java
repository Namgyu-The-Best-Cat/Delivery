package com.bestcat.delivery.area.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name="p_service_area")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    @Id
    @UuidGenerator
    private UUID area_id;

    private String city;

    private String area_name;


}
