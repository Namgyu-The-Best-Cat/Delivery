package com.bestcat.delivery.ai.entity;

import com.bestcat.delivery.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_ai_log")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AiLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String reqText;

    private String respText;
}
