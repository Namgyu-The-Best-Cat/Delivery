package com.bestcat.delivery.user.web;

import static com.bestcat.delivery.common.type.ResponseMessage.GET_USER_INFO_SUCCESS;
import static com.bestcat.delivery.common.type.ResponseMessage.SIGNIN_SUCCESS;
import static com.bestcat.delivery.common.type.ResponseMessage.SIGNUP_SUCCESS;

import com.bestcat.delivery.common.dto.SuccessResponse;
import com.bestcat.delivery.common.type.ResponseMessage;
import com.bestcat.delivery.common.util.UserDetailsImpl;
import com.bestcat.delivery.user.dto.DeliveryAddressRequestDto;
import com.bestcat.delivery.user.dto.DeliveryAddressResponseDto;
import com.bestcat.delivery.user.dto.SignupRequestDto;
import com.bestcat.delivery.user.dto.UserInfoDto;
import com.bestcat.delivery.user.service.UserService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<ResponseMessage>> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);

        return ResponseEntity.ok(SuccessResponse.of(SIGNUP_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<UserInfoDto>> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(
                SuccessResponse.of(GET_USER_INFO_SUCCESS, userService.getUserInfo(userDetails.getUser())));
    }

    @GetMapping("/{userId}/delivery")
    @Secured({"ROLE_CUSTOMER"})
    public ResponseEntity<DeliveryAddressResponseDto> getDeliveryAddress(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getDeliveryAddress(userId, userDetails.getUser()));
    }

    @PostMapping("/{userId}/delivery")
    @Secured({"ROLE_CUSTOMER"})
    public ResponseEntity<DeliveryAddressResponseDto> addDeliveryAddress(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable UUID userId, @RequestBody @Valid DeliveryAddressRequestDto requestDto) {
        return ResponseEntity.ok(userService.addDeliveryAddress(userId, userDetails.getUser(), requestDto));
    }

    @PutMapping("/{userId}/delivery/{deliveryId}")
    @Secured({"ROLE_CUSTOMER"})
    public ResponseEntity<DeliveryAddressResponseDto> updateDeliveryAddress(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                            @PathVariable UUID userId,
                                                                            @PathVariable UUID deliveryId,
                                                                            @RequestBody @Valid DeliveryAddressRequestDto requestDto) {
        return ResponseEntity.ok(userService.updateDeliveryAddress(userId, userDetails.getUser(), deliveryId, requestDto));
    }
}
