package com.bestcat.delivery.user.repository;

import com.bestcat.delivery.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmailAndDeletedAtIsNull(String email);

    boolean existsByUsernameAndDeletedAtIsNull(String username);

    Optional<User> findByUsernameAndDeletedAtIsNull(String username);

    boolean existsByNicknameAndDeletedAtIsNull(String nickname);

    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndDeletedAtIsNull(UUID id);
}
