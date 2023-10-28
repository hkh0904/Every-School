package com.everyschool.schoolservice.domain.schoolclass.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class SchoolClassQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SchoolClassQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public boolean existByTeacherIdAndSchoolYear(Long teacherId, int year) {
        return false;
    }
}
