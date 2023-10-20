package com.everyschool.userservice.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public UserQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public boolean existEmail(String email) {
        return false;
    }
}
