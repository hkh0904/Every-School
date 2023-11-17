package com.everyschool.schoolservice.domain.schooluser.repository;

import com.everyschool.schoolservice.api.web.controller.client.response.DescendantInfo;
import com.everyschool.schoolservice.api.web.controller.client.response.StudentInfo;
import com.everyschool.schoolservice.api.service.schooluser.dto.MyClassStudentDto;
import com.everyschool.schoolservice.domain.schooluser.SchoolUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.everyschool.schoolservice.domain.school.QSchool.school;
import static com.everyschool.schoolservice.domain.schoolclass.QSchoolClass.schoolClass;
import static com.everyschool.schoolservice.domain.schooluser.QSchoolUser.schoolUser;
import static com.everyschool.schoolservice.domain.schooluser.UserType.*;

@Repository
public class SchoolUserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SchoolUserQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<MyClassStudentDto> findBySchoolClassId(Long schoolClassId) {
        return queryFactory
            .select(Projections.constructor(
                MyClassStudentDto.class,
                schoolUser.userId,
                schoolUser.studentNumber
            ))
            .from(schoolUser)
            .where(
                schoolUser.schoolClass.id.eq(schoolClassId),
                schoolUser.userTypeId.eq(STUDENT.getCode()),
                schoolUser.isDeleted.isFalse()
            )
            .fetch();
    }

    public List<SchoolUser> findStudentAndParentBySchoolClassId(Long schoolClassId) {
        List<Integer> userType = List.of(STUDENT.getCode(), FATHER.getCode(), MOTHER.getCode());
        return queryFactory
            .select(schoolUser)
            .from(schoolUser)
            .where(
                schoolUser.isDeleted.isFalse(),
                schoolUser.schoolClass.id.eq(schoolClassId),
                schoolUser.userTypeId.in(userType)
            )
            .orderBy(schoolUser.studentNumber.asc())
            .fetch();
    }

    public Optional<StudentInfo> findByUserId(Long userId) {
        StudentInfo content = queryFactory
            .select(Projections.constructor(
                StudentInfo.class,
                schoolUser.schoolClass.grade,
                schoolUser.schoolClass.classNum,
                schoolUser.studentNumber
            ))
            .from(schoolUser)
            .join(schoolUser.schoolClass, schoolClass)
            .where(
                schoolUser.userId.eq(userId)
            )
            .fetchOne();

        return Optional.ofNullable(content);
    }

    public List<SchoolUser> findByUserIdIn(List<Long> userIds) {
        return queryFactory
            .select(schoolUser)
            .from(schoolUser)
            .where(schoolUser.userId.in(userIds))
            .fetch();
    }

    public List<DescendantInfo> findDescendantInfo(List<Long> userIds) {
        return queryFactory
            .select(Projections.constructor(
                DescendantInfo.class,
                schoolUser.userId,
                schoolUser.school.name,
                schoolUser.schoolYear,
                schoolUser.schoolClass.grade,
                schoolUser.schoolClass.classNum,
                schoolUser.studentNumber
            ))
            .from(schoolUser)
            .join(schoolUser.schoolClass, schoolClass)
            .join(schoolUser.school, school)
            .where(
                schoolUser.isDeleted.isFalse(),
                schoolUser.userId.in(userIds)
            )
            .fetch();
    }

    public Optional<Long> findByUserIdAndSchoolYear(Long userId, int schoolYear) {
        Long teacherId = queryFactory
            .select(schoolUser.schoolClass.id)
            .from(schoolUser)
            .where(
                schoolUser.userId.eq(userId),
                schoolUser.schoolYear.eq(schoolYear)
            )
            .fetchFirst();
        return Optional.ofNullable(teacherId);
    }

    public Optional<SchoolUser> findTeacher(Long schoolClassId) {
        SchoolUser content = queryFactory
            .select(schoolUser)
            .from(schoolUser)
            .where(
                schoolUser.schoolClass.id.eq(schoolClassId),
                schoolUser.userTypeId.eq(TEACHER.getCode())
            )
            .fetchFirst();
        return Optional.ofNullable(content);
    }

    public List<SchoolUser> findStudent(Long schoolClassId) {
        return queryFactory
            .select(schoolUser)
            .from(schoolUser)
            .where(
                schoolUser.schoolClass.id.eq(schoolClassId),
                schoolUser.userTypeId.eq(STUDENT.getCode())
            )
            .fetch();
    }
}
