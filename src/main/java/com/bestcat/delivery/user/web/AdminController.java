package com.bestcat.delivery.user.web;

import static com.bestcat.delivery.common.type.ResponseMessage.GET_USER_INFO_SUCCESS;

import com.bestcat.delivery.common.dto.SuccessResponse;
import com.bestcat.delivery.common.util.UserDetailsImpl;
import com.bestcat.delivery.user.dto.UserDetailsInfoDto;
import com.bestcat.delivery.user.dto.UserInfoDto;
import com.bestcat.delivery.user.entity.SortType;
import com.bestcat.delivery.user.service.AdminService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/search-users")
    @Secured({"ROLE_MASTER", "ROLE_MANAGER"})
    public ResponseEntity<SuccessResponse<Page<UserDetailsInfoDto>>> getUserInfo(
              @RequestParam(required = false, name = "search") String search,
              @RequestParam(required = false, defaultValue = "USERNAME", name = "type") SortType searchType,
              @RequestParam(required = false, name = "role") String roleType,
              @RequestParam(defaultValue = "0", name = "page") Integer page,
              @RequestParam(defaultValue = "10", name = "size") Integer size,
              @RequestParam(defaultValue = "ID", name = "sort") SortType sort,
              @RequestParam(defaultValue = "true", name = "isAsc") boolean isAsc) {
        return ResponseEntity.ok().body(
                SuccessResponse.of(GET_USER_INFO_SUCCESS, adminService.getUserInfo(search, searchType, roleType, page, size, sort, isAsc)));
    }
}
