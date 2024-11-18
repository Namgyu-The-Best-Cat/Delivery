package com.bestcat.delivery.user.repository;

import com.bestcat.delivery.user.entity.User;
import java.nio.channels.FileChannel;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID>, UserCustomRepository {
    boolean existsByEmailAndDeletedAtIsNull(String email);

    boolean existsByUsernameAndDeletedAtIsNull(String username);

    Optional<User> findByUsernameAndDeletedAtIsNull(String username);

    boolean existsByNicknameAndDeletedAtIsNull(String nickname);

    Optional<User> findByIdAndDeletedAtIsNull(UUID id);


}
