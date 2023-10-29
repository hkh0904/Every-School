package com.everyschool.schoolservice.domain.schoolapply.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class SchoolApplyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SchoolApplyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
