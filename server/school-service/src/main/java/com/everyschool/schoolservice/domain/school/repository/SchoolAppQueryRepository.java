package com.everyschool.schoolservice.domain.school.repository;

import com.everyschool.schoolservice.api.controller.school.response.SchoolDetailResponse;
import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.everyschool.schoolservice.domain.school.QSchool.school;

/**
 * 앱 학교 조회용 저장소
 *
 * @author 임우택
 */
@Repository
public class SchoolAppQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SchoolAppQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 학교 이름이 포함된 학교 목록 조회
     *
     * @param query 쿼리 내용
     * @return 조회된 학교 목록
     */
    public List<SchoolResponse> findByNameLike(String query) {
        return queryFactory
            .select(
                Projections.constructor(
                    SchoolResponse.class,
                    school.id,
                    school.name,
                    school.address
                ))
            .from(school)
            .where(
                school.isDeleted.isFalse(),
                school.name.like(likeFormat(query))
            )
            .orderBy(school.name.asc())
            .fetch();
    }

    public Optional<SchoolDetailResponse> findById(Long schoolId) {
        SchoolDetailResponse content = queryFactory
            .select(Projections.constructor(
                SchoolDetailResponse.class,
                school.id,
                school.name,
                school.address,
                school.url,
                school.tel,
                school.openDate
            ))
            .from(school)
            .where(
                school.id.eq(schoolId),
                school.isDeleted.isFalse()
            )
            .fetchOne();

        return Optional.ofNullable(content);
    }

    private String likeFormat(String query) {
        return "%" + query + "%";
    }
}
