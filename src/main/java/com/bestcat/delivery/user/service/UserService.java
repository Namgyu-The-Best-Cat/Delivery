package com.bestcat.delivery.user.service;

import static com.bestcat.delivery.common.type.ErrorCode.AREA_NOT_FOUND;
import static com.bestcat.delivery.common.type.ErrorCode.DELIVERY_ADDRESS_NOT_FOUND;
import static com.bestcat.delivery.common.type.ErrorCode.USER_ID_MISMATCH;
import static com.bestcat.delivery.common.type.ErrorCode.USER_NOT_FOUND;

import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.area.repository.AreaRepository;
import com.bestcat.delivery.common.exception.RestApiException;
import com.bestcat.delivery.user.dto.DeliveryAddressRequestDto;
import com.bestcat.delivery.user.dto.DeliveryAddressResponseDto;
import com.bestcat.delivery.user.dto.SignupRequestDto;
import com.bestcat.delivery.user.dto.UserInfoDto;
import com.bestcat.delivery.user.entity.DeliveryAddress;
import com.bestcat.delivery.user.entity.User;
import com.bestcat.delivery.user.repository.DeliveryAddressRepository;
import com.bestcat.delivery.user.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DeliveryAddressRepository deliveryAddressRepository;
     private final AreaRepository areaRepository;

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

    public UserInfoDto getUserInfo(User user) {
        User getUser = userRepository.findByIdAndDeletedAtIsNull(user.getId()).orElseThrow(() -> new RestApiException(USER_NOT_FOUND));

        return new UserInfoDto(getUser);
    }

    public DeliveryAddressResponseDto addDeliveryAddress(UUID userId, User user, DeliveryAddressRequestDto requestDto) {
        if(!userId.equals(user.getId())) {
            throw new RestApiException(USER_ID_MISMATCH);
        }

        Area area = areaRepository.findByCityAndAreaNameAndDeletedAtIsNull(requestDto.city(), requestDto.areaName()).orElseThrow(() ->
                new RestApiException(AREA_NOT_FOUND));

        DeliveryAddress deliveryAddress = deliveryAddressRepository.save(requestDto.toEntity(user, area));

        return new DeliveryAddressResponseDto(deliveryAddress);
    }

    public DeliveryAddressResponseDto getDeliveryAddress(UUID userId, User user) {
        if(!userId.equals(user.getId())) {
            throw new RestApiException(USER_ID_MISMATCH);
        }

        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByUserAndDeletedAtIsNull(user).orElseThrow(() ->
                new RestApiException(DELIVERY_ADDRESS_NOT_FOUND));

        return new DeliveryAddressResponseDto(deliveryAddress);
    }

    public DeliveryAddressResponseDto updateDeliveryAddress(UUID userId, User user, UUID deliveryId, DeliveryAddressRequestDto requestDto) {
        if(!userId.equals(user.getId())) {
            throw new RestApiException(USER_ID_MISMATCH);
        }

        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(deliveryId).orElseThrow(() ->
                new RestApiException(DELIVERY_ADDRESS_NOT_FOUND));

        if(!userId.equals(deliveryAddress.getUser().getId())) {
            throw new RestApiException(USER_ID_MISMATCH);
        }

        Area area = areaRepository.findByCityAndAreaNameAndDeletedAtIsNull(requestDto.city(), requestDto.areaName()).orElseThrow(() ->
                new RestApiException(AREA_NOT_FOUND));

        deliveryAddress.updateToAreaAndDetailedAddress(area, requestDto.detailAddress());

        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);

        return new DeliveryAddressResponseDto(deliveryAddress);
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
