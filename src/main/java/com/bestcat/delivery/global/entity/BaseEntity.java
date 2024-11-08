package com.bestcat.delivery.global.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private Timestamp createdAt;

    @Column(updatable = false)
    private UUID createdBy;

    @LastModifiedDate
    private Timestamp updatedAt;

    private UUID updatedBy;

    private Timestamp deletedAt;

    private UUID deletedBy;

}