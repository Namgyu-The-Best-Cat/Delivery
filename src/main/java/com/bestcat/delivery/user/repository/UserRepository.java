package com.bestcat.delivery.user.repository;

import com.bestcat.delivery.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    boolean existsByUsernameAndDeletedAtIsNull(String username);

    boolean existsByEmailAndDeletedAtIsNull(String email);

    Optional<User> findByUsername(String username);

    boolean existsByNicknameAndDeletedAtIsNull(String nickname);
}
