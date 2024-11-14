package com.bestcat.delivery.user.web;

import static com.bestcat.delivery.common.type.ResponseMessage.NICKNAME_CANT_USE;
import static com.bestcat.delivery.common.type.ResponseMessage.NICKNAME_CAN_USE;
import static com.bestcat.delivery.common.type.ResponseMessage.USERNAME_CANT_USE;
import static com.bestcat.delivery.common.type.ResponseMessage.USERNAME_CAN_USE;

import com.bestcat.delivery.common.dto.SuccessResponse;
import com.bestcat.delivery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/check-nickname")
    public ResponseEntity<SuccessResponse<String>> checkNickname(@RequestParam(name = "nickname") String nickname) {
        return !userService.checkNickname(nickname) ? ResponseEntity.ok().body(SuccessResponse.of(NICKNAME_CAN_USE)) :
                ResponseEntity.badRequest().body(SuccessResponse.of(NICKNAME_CANT_USE));
    }

    @GetMapping("/check-username")
    public ResponseEntity<SuccessResponse<String>> checkUsername(@RequestParam(name = "username") String username) {
        return !userService.checkUsername(username) ? ResponseEntity.ok().body(SuccessResponse.of(USERNAME_CAN_USE)) :
                ResponseEntity.badRequest().body(SuccessResponse.of(USERNAME_CANT_USE));
    }

}
