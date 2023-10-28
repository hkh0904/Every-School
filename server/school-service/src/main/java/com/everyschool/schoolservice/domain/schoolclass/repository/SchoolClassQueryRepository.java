package com.everyschool.schoolservice.domain.schoolclass.repository;

import com.everyschool.schoolservice.domain.schoolclass.QSchoolClass;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.everyschool.schoolservice.domain.schoolclass.QSchoolClass.schoolClass;

@Repository
public class SchoolClassQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SchoolClassQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public boolean existByTeacherIdAndSchoolYear(Long teacherId, int schoolYear) {
        Long result = queryFactory
            .select(schoolClass.id)
            .from(schoolClass)
            .where(
                schoolClass.teacherId.eq(teacherId),
                schoolClass.schoolYear.eq(schoolYear)
            )
            .fetchFirst();
        return result != null;
    }
}
