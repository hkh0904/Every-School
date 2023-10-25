package com.everyschool.userservice.domain.user.repository;

import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.Optional;

import static com.everyschool.userservice.domain.user.QUser.user;

@Repository
public class UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public UserQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public boolean existEmail(String email) {
        Long result = queryFactory
            .select(user.id)
            .from(user)
            .where(user.email.eq(email))
            .fetchFirst();
        return result != null;
    }

    public Optional<UserInfoResponse> findByEmail(String email) {
        UserInfoResponse content = queryFactory
            .select(Projections.constructor(
                UserInfoResponse.class,
                user.name,
                Expressions.asString(email),
                user.name,
                user.birth,
                user.createdDate
            ))
            .from(user)
            .where(user.email.eq(email))
            .fetchOne();
        return Optional.ofNullable(content);
    }
}
