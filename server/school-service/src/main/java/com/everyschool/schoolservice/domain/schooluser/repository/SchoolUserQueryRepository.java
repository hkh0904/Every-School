package com.everyschool.schoolservice.domain.schooluser.repository;

import com.everyschool.schoolservice.api.service.schooluser.dto.SearchMyClassStudentDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.schoolservice.domain.schooluser.QSchoolUser.schoolUser;

@Repository
public class SchoolUserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SchoolUserQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<SearchMyClassStudentDto> findBySchoolClassId(Long schoolClassId) {
        return queryFactory
            .select(Projections.constructor(
                SearchMyClassStudentDto.class,
                schoolUser.userId,
                schoolUser.studentNum
            ))
            .from(schoolUser)
            .where(schoolUser.schoolClass.id.eq(schoolClassId))
            .fetch();
    }
}
