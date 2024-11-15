package com.bestcat.delivery.user.entity;


import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "p_delivery_address")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeliveryAddress extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID deliveryAddressId;

    @JoinColumn(name = "p_service_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private Area area;

    @JoinColumn(name = "p_user")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String detailedAddress;

    public void updateToAreaAndDetailedAddress(Area area, String detailedAddress) {
        this.area = area;
        this.detailedAddress = detailedAddress;
    }

}
