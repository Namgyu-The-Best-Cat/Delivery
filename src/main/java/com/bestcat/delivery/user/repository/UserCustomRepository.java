package com.bestcat.delivery.user.repository;

import com.bestcat.delivery.user.entity.SortType;
import com.bestcat.delivery.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCustomRepository{
    Page<User> findAll(String search, SortType searchType, String roleType, Pageable pageable, SortType sort, boolean isAsc);

}
