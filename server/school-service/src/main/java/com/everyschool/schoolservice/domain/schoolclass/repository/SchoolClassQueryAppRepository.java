package com.everyschool.schoolservice.domain.schoolclass.repository;

import com.everyschool.schoolservice.api.web.controller.client.response.SchoolClassInfo;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.Optional;

import static com.everyschool.schoolservice.domain.school.QSchool.school;
import static com.everyschool.schoolservice.domain.schoolclass.QSchoolClass.schoolClass;

/**
 * 앱 학교 신청 조회용 저장소
 *
 * @author 임우택
 */
@Repository
public class SchoolClassQueryAppRepository {

    private final JPAQueryFactory queryFactory;

    public SchoolClassQueryAppRepository(EntityManager em) {
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

    /**
     * 학급 정보로 학급 엔티티 조회
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param grade      학년
     * @param classNum   반
     * @return 조회된 학급 엔티티
     */
    public Optional<SchoolClass> findByInfo(int schoolYear, Long schoolId, int grade, int classNum) {
        SchoolClass result = queryFactory
            .select(schoolClass)
            .from(schoolClass)
            .where(
                schoolClass.schoolYear.eq(schoolYear),
                schoolClass.school.id.eq(schoolId),
                schoolClass.grade.eq(grade),
                schoolClass.classNum.eq(classNum)
            )
            .fetchFirst();
        return Optional.ofNullable(result);
    }

    public SchoolClassInfo findInfoById(Long schoolClassId) {
        return queryFactory
            .select(Projections.constructor(
                SchoolClassInfo.class,
                schoolClass.school.name,
                schoolClass.schoolYear,
                schoolClass.grade,
                schoolClass.classNum
            ))
            .from(schoolClass)
            .join(schoolClass.school, school)
            .where(schoolClass.id.eq(schoolClassId))
            .fetchFirst();
    }

    public Long findTeacherById(Long schoolClassId) {
        return queryFactory
            .select(schoolClass.teacherId)
            .from(schoolClass)
            .where(schoolClass.id.eq(schoolClassId))
            .fetchFirst();
    }
}
