package com.bestcat.delivery.user.service;

import static com.bestcat.delivery.common.type.ErrorCode.ALREADY_EXIST_NICKNAME;
import static com.bestcat.delivery.common.type.ErrorCode.NEW_PASSWORD_SAME_AS_CURRENT;
import static com.bestcat.delivery.common.type.ErrorCode.PASSWORD_MISMATCH;
import static com.bestcat.delivery.common.type.ErrorCode.USER_ID_MISMATCH;
import static com.bestcat.delivery.common.type.ErrorCode.USER_NOT_FOUND;

import com.bestcat.delivery.common.exception.RestApiException;
import com.bestcat.delivery.user.dto.NicknameRequestDto;
import com.bestcat.delivery.user.dto.PasswordRequestDto;
import com.bestcat.delivery.user.dto.SignupRequestDto;
import com.bestcat.delivery.user.entity.RoleType;
import com.bestcat.delivery.user.entity.User;
import com.bestcat.delivery.user.repository.UserRepository;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto signupRequestDto) {
        User user = signupRequestDto.toEntity();

        if(checkUsername(user.getUsername())) {
            throw new IllegalArgumentException("중복된 아이디입니다. 다른 아이디를 사용해주세요.");
        }

        if(checkEmail(user.getEmail())) {
            throw new IllegalArgumentException("중복된 이메일입니다. 다른 이메일을 사용해주세요.");
        }

        if(checkNickname(user.getNickname())) {
            throw new IllegalArgumentException("중복된 닉네임입니다. 다른 닉네임을 사용해주세요.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public String updateNickname(UUID userId, User user, @Valid NicknameRequestDto requestDto) {
        validateUserIdIfNotAdminOrManager(userId, user);

        if(checkNickname(requestDto.nickname())) {
            throw new RestApiException(ALREADY_EXIST_NICKNAME);
        }

        user.updateNickname(requestDto.nickname());
        userRepository.save(user);

        return requestDto.nickname();
    }

    public void deleteUser(UUID userId, User user) {
        validateUserIdIfNotAdminOrManager(userId, user);

        User userToDelete = userRepository.findById(userId).orElseThrow(() ->
                new RestApiException(USER_NOT_FOUND));

        userToDelete.updateDeleted();

        userRepository.save(userToDelete);
    }

    public void updatePassword(UUID userId, User user, PasswordRequestDto requestDto) {
        if(!userId.equals(user.getId())) {
            throw new RestApiException(USER_ID_MISMATCH);
        }

        if(requestDto.password().equals(requestDto.newPassword())) {
            throw new RestApiException(NEW_PASSWORD_SAME_AS_CURRENT);
        }

        if(!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new RestApiException(PASSWORD_MISMATCH);
        }

        user.updatePassword(passwordEncoder.encode(requestDto.newPassword()));

        userRepository.save(user);
    }

    private void validateUserIdIfNotAdminOrManager(UUID userId, User user) {
        if((user.getRole() == RoleType.CUSTOMER || user.getRole() == RoleType.OWNER) && !user.getId().equals(userId)) {
            throw new RestApiException(USER_ID_MISMATCH);
        }
    }

    public boolean checkEmail(String email) {
        return userRepository.existsByEmailAndDeletedAtIsNull(email);
    }

    public boolean checkUsername(String username) {
        return userRepository.existsByUsernameAndDeletedAtIsNull(username);
    }

    public boolean checkNickname(String nickname) {
        return userRepository.existsByNicknameAndDeletedAtIsNull(nickname);
    }

}
