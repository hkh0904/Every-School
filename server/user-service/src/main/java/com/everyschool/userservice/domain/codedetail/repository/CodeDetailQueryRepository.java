package com.everyschool.userservice.domain.codedetail.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CodeDetailQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CodeDetailQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
