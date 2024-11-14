package com.bestcat.delivery.user.web;

import static com.bestcat.delivery.common.type.ResponseMessage.DELETE_USER_SUCCESS;
import static com.bestcat.delivery.common.type.ResponseMessage.GET_USER_INFO_SUCCESS;
import static com.bestcat.delivery.common.type.ResponseMessage.NICKNAME_UPDATE_SUCCESS;
import static com.bestcat.delivery.common.type.ResponseMessage.PASSWORD_UPDATE_SUCCESS;
import static com.bestcat.delivery.common.type.ResponseMessage.SIGN_UP_SUCCESS;

import com.bestcat.delivery.common.dto.SuccessResponse;
import com.bestcat.delivery.common.type.ResponseMessage;
import com.bestcat.delivery.common.util.UserDetailsImpl;
import com.bestcat.delivery.user.dto.NicknameRequestDto;
import com.bestcat.delivery.user.dto.PasswordRequestDto;
import com.bestcat.delivery.user.dto.SignupRequestDto;
import com.bestcat.delivery.user.dto.UserInfoDto;
import com.bestcat.delivery.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<ResponseMessage>> signup(
            @Valid @RequestBody SignupRequestDto requestDto) {

        userService.signup(requestDto);
        return ResponseEntity.ok(SuccessResponse.of(SIGN_UP_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<UserInfoDto>> getUserInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok().body(SuccessResponse.of(GET_USER_INFO_SUCCESS, new UserInfoDto(userDetails)));
    }

    @DeleteMapping("/{userId}")
    @Secured({"ROLE_MASTER", "ROLE_MANAGER", "ROLE_OWNER", "ROLE_CUSTOMER"})
    public ResponseEntity<SuccessResponse<String>> deleteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable UUID userId,
            HttpServletResponse response) {

        userService.deleteUser(userId, userDetails.getUser(), response);
        return ResponseEntity.ok(SuccessResponse.of(DELETE_USER_SUCCESS));
    }

    @PatchMapping("/{userId}/password")
    @Secured({"ROLE_MASTER", "ROLE_MANAGER", "ROLE_OWNER", "ROLE_CUSTOMER"})
    public ResponseEntity<SuccessResponse<String>> updatePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable UUID userId,
            @RequestBody @Valid PasswordRequestDto requestDto) {

        userService.updatePassword(userId, userDetails.getUser(), requestDto);
        return ResponseEntity.ok(SuccessResponse.of(PASSWORD_UPDATE_SUCCESS));
    }

    @PatchMapping("/{userId}/nickname")
    @Secured({"ROLE_MASTER", "ROLE_MANAGER", "ROLE_OWNER", "ROLE_CUSTOMER"})
    public ResponseEntity<SuccessResponse<String>> updateNickname(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable UUID userId,
            @RequestBody @Valid NicknameRequestDto nickname) {

        return ResponseEntity.ok(SuccessResponse.of(
                NICKNAME_UPDATE_SUCCESS, userService.updateNickname(userId, userDetails.getUser(), nickname)
        ));
    }


}
