package com.bestcat.delivery.ai.repository;

import com.bestcat.delivery.ai.entity.AiLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AiRepository extends JpaRepository<AiLog, UUID> {
}
