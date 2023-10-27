package com.everyschool.schoolservice.domain.school.repository;

import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.everyschool.schoolservice.domain.school.QSchool.school;

@Repository
public class SchoolQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SchoolQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<SchoolResponse> findByName(String query) {
        return queryFactory
            .select(Projections.constructor(
                SchoolResponse.class,
                school.id,
                school.name,
                school.address
            ))
            .from(school)
            .where(
                school.name.like("%" + query + "%"),
                school.isDeleted.isFalse()
            )
            .fetch();
    }

    public Optional<SchoolResponse> findById(Long schoolId) {
        SchoolResponse content = queryFactory
            .select(Projections.constructor(
                SchoolResponse.class,
                school.name,
                school.address,
                school.url,
                school.tel
            ))
            .from(school)
            .where(school.id.eq(schoolId))
            .fetchOne();

        return Optional.ofNullable(content);
    }
}
