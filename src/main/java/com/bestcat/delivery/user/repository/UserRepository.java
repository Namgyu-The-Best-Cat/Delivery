package com.bestcat.delivery.user.repository;

import com.bestcat.delivery.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    Optional<User> findByUsername(String username);
}
