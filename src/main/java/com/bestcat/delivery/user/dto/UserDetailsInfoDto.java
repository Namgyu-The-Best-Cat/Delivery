package com.bestcat.delivery.user.dto;

import com.bestcat.delivery.user.entity.RoleType;
import com.bestcat.delivery.user.entity.User;
import java.util.UUID;

public record UserDetailsInfoDto(
        UUID id,
        String username,
        String email,
        String nickname,
        RoleType role,
        boolean isPublic
) {

    public UserDetailsInfoDto(User user) {
        this(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getNickname(),
                user.getRole(),
                user.isPublic())
        ;
    }

}
