package com.bestcat.delivery.common.config;

import com.bestcat.delivery.common.util.UserDetailsImpl;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetailsImpl)) {
            return Optional.empty();
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Optional.of(userDetails.getUser().getId());  // UUID 타입의 사용자 ID 반환
    }
}