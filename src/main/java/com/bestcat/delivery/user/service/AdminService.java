package com.bestcat.delivery.user.service;

import com.bestcat.delivery.user.dto.UserDetailsInfoDto;
import com.bestcat.delivery.user.entity.SortType;
import com.bestcat.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;


    public Page<UserDetailsInfoDto> getUserInfo(String query, SortType searchType, String roleType, Integer page, Integer size, SortType sort,
                                                boolean isAsc) {
        Pageable pageable = PageRequest.of(page, size);

        return userRepository.findAll(query, searchType, roleType, pageable, sort, isAsc)
                .map(UserDetailsInfoDto::new);
    }

}
