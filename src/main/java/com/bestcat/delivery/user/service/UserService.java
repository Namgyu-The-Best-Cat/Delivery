package com.bestcat.delivery.user.service;

import com.bestcat.delivery.user.dto.SignupRequestDto;
import com.bestcat.delivery.user.entity.User;
import com.bestcat.delivery.user.repository.UserRepository;
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



    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean checkNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

}
