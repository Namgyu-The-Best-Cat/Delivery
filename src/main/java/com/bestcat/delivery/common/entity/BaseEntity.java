package com.bestcat.delivery.common.entity;

import static com.bestcat.delivery.common.type.ErrorCode.NOT_AUTHENTICATED_USER;

import com.bestcat.delivery.common.exception.RestApiException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.bestcat.delivery.common.config.AuditorAwareImpl;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private Timestamp createdAt;

    @Setter
    @CreatedBy
    private UUID createdBy;

    @LastModifiedDate
    private Timestamp updateAt;

    @Setter
    @LastModifiedBy
    private UUID updatedBy;

    private Timestamp deletedAt;

    private UUID deletedBy;

    public void updateDeleted() {
        // 현재 시간으로 삭제 시간 설정
        this.deletedAt = new Timestamp(System.currentTimeMillis());

        // 삭제한 사용자를 설정
        this.deletedBy = getCurrentAuditorId();
    }

    private UUID getCurrentAuditorId() {
        return new AuditorAwareImpl().getCurrentAuditor().orElseThrow(() ->
                new RestApiException(NOT_AUTHENTICATED_USER));
    }
}
