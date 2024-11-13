package com.bestcat.delivery.user.web;

import static com.bestcat.delivery.common.type.ResponseMessage.GET_USER_INFO_SUCCESS;
import static com.bestcat.delivery.common.type.ResponseMessage.SIGNIN_SUCCESS;
import static com.bestcat.delivery.common.type.ResponseMessage.SIGNUP_SUCCESS;

import com.bestcat.delivery.common.dto.SuccessResponse;
import com.bestcat.delivery.common.type.ResponseMessage;
import com.bestcat.delivery.common.util.UserDetailsImpl;
import com.bestcat.delivery.user.dto.SignupRequestDto;
import com.bestcat.delivery.user.dto.UserInfoDto;
import com.bestcat.delivery.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<SuccessResponse<ResponseMessage>> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);

        return ResponseEntity.ok(SuccessResponse.of(SIGNUP_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<UserInfoDto>> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(
                SuccessResponse.of(GET_USER_INFO_SUCCESS, new UserInfoDto(userDetails)));
    }

}
