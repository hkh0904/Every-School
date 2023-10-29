package com.everyschool.schoolservice.domain.schooluser.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class SchoolUserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SchoolUserQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

}
