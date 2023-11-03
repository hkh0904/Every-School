package com.everyschool.schoolservice.domain.schooluser.repository;

import com.everyschool.schoolservice.api.controller.client.response.StudentInfo;
import com.everyschool.schoolservice.api.service.schooluser.dto.MyClassStudentDto;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.everyschool.schoolservice.domain.schooluser.SchoolUser;
import com.everyschool.schoolservice.domain.schooluser.UserType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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

    public List<SchoolUser> findParentBySchoolClassId(Long schoolClassId) {
        List<Integer> userType = List.of(STUDENT.getCode(), PARENT.getCode());
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
}
