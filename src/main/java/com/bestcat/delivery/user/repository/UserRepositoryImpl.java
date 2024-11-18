package com.bestcat.delivery.user.repository;

import static com.bestcat.delivery.user.entity.SortType.ID;
import static com.bestcat.delivery.user.entity.SortType.NICKNAME;
import static com.bestcat.delivery.user.entity.SortType.USERNAME;

import com.bestcat.delivery.user.entity.QUser;
import com.bestcat.delivery.user.entity.RoleType;
import com.bestcat.delivery.user.entity.SortType;
import com.bestcat.delivery.user.entity.User;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<User> findAll(String search, SortType searchType, String roleType, Pageable pageable, SortType sort, boolean isAsc) {
        QUser user = QUser.user;

        // 조건 설정
        BooleanExpression condition = search(search, searchType, user);

        // QueryDSL 쿼리
        List<User> results = queryFactory
                .selectFrom(user)
                .where(condition, searchRoleType(roleType))
                .orderBy(orderSpecifier(sort, isAsc))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 카운트
        JPAQuery<Long> total = queryFactory
                .select(user.count())
                .from(user)
                .where(condition);

        return new PageImpl<>(results, pageable, total.fetchOne() == null ? 0 : total.fetchOne());
    }

    private Predicate searchRoleType(String roleType) {
        if(roleType == null || roleType.isEmpty()) {
            return QUser.user.role.in(RoleType.CUSTOMER, RoleType.OWNER);
        }

        if(roleType.equals(RoleType.CUSTOMER.toString())) {
            return QUser.user.role.in(RoleType.CUSTOMER);
        }

        if (roleType.equalsIgnoreCase(RoleType.OWNER.toString())) {
            return QUser.user.role.in(RoleType.OWNER);
        }

        return null;
    }

    private OrderSpecifier<?> orderSpecifier(SortType sortType, boolean isAsc) {
        if(sortType == ID) {
            return isAsc ? QUser.user.id.asc() : QUser.user.id.desc();
        }

        if(sortType == USERNAME) {
            return isAsc ? QUser.user.username.asc() : QUser.user.username.desc();
        }

        if(sortType == NICKNAME) {
            return isAsc ? QUser.user.nickname.asc() : QUser.user.nickname.desc();
        }

        return isAsc ? QUser.user.email.asc() : QUser.user.email.desc();

    }

    private BooleanExpression search(String search, SortType searchType, QUser user) {
        if(search == null || search.isEmpty()) {
            return null;
        }

        if(searchType == ID) {
            return user.id.eq(UUID.fromString(search));
        }

        if(searchType == USERNAME) {
            return user.username.containsIgnoreCase(search);
        }

        if(searchType == NICKNAME) {
            return user.nickname.containsIgnoreCase(search);
        }

        return user.email.containsIgnoreCase(search);
    }

}
