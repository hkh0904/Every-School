package com.everyschool.reportservice.domain.report.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ReportQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ReportQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
