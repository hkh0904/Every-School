package com.everyschool.consultservice.domain.consultschedule.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ConsultScheduleWebQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ConsultScheduleWebQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
