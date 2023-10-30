package com.everyschool.schoolservice.domain.schoolclass.repository;

import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.Optional;

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

    public boolean existSchoolClass(Long schoolId, int schoolYear, int grade, int classNum) {
        Long result = queryFactory
            .select(schoolClass.id)
            .from(schoolClass)
            .where(
                schoolClass.school.id.eq(schoolId),
                schoolClass.schoolYear.eq(schoolYear),
                schoolClass.grade.eq(grade),
                schoolClass.classNum.eq(classNum)
            )
            .fetchFirst();
        return result != null;
    }

    public Optional<SchoolClass> findByInfo(Long schoolId, Integer schoolYear, Integer grade, Integer classNum) {
        SchoolClass result = queryFactory
            .select(schoolClass)
            .from(schoolClass)
            .where(
                schoolClass.school.id.eq(schoolId),
                schoolClass.schoolYear.eq(schoolYear),
                schoolClass.grade.eq(grade),
                schoolClass.classNum.eq(classNum)
            )
            .fetchFirst();
        return Optional.ofNullable(result);
    }
}
