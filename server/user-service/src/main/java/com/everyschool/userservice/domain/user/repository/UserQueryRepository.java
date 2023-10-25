package com.everyschool.userservice.domain.user.repository;

import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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

    public UserInfoResponse findByEmail(String email) {
        return null;
    }
}
