package com.everyschool.consultservice.domain.consult.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ConsultQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ConsultQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
