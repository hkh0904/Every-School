package com.everyschool.schoolservice.domain.schoolapply.repository;

import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.schoolservice.domain.schoolapply.QSchoolApply.*;

/**
 * 학교 신청 조회용 저장소
 *
 * @author 임우택
 */
@Repository
public class SchoolApplyWebQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SchoolApplyWebQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<SchoolApply> findAllByCond(Long schoolClassId, int schoolYear, int status) {
        return queryFactory
            .select(schoolApply)
            .from(schoolApply)
            .where(
                schoolApply.isDeleted.isFalse(),
                schoolApply.schoolClass.id.eq(schoolClassId),
                schoolApply.schoolYear.eq(schoolYear),
                status(status)
            )
            .fetch();
    }

    private BooleanExpression status(int status) {
        if (status == 0) {
            return schoolApply.isApproved.isFalse().and(schoolApply.rejectedReason.isNull());
        }

        if (status == 1) {
            return schoolApply.isApproved.isTrue().and(schoolApply.rejectedReason.isNull());
        }

        return schoolApply.isApproved.isFalse().and(schoolApply.rejectedReason.isNotEmpty());
    }

    /**
     * 승인 대기 중인 신청 목록 조회
     *
     * @param schoolClassId 학급 아이디
     * @param schoolYear    학년도
     * @return 조회된 신청 목록
     */
    public List<SchoolApply> findWaitSchoolApply(Long schoolClassId, Integer schoolYear) {
        return queryFactory
            .select(schoolApply)
            .from(schoolApply)
            .where(
                schoolApply.isDeleted.isFalse(),
                schoolApply.schoolClass.id.eq(schoolClassId),
                schoolApply.schoolYear.eq(schoolYear),
                schoolApply.isApproved.isFalse(),
                schoolApply.rejectedReason.isNull()
            )
            .orderBy(
                schoolApply.createdDate.desc()
            )
            .fetch();
    }

    /**
     * 승인된 신청 목록 조회
     *
     * @param schoolClassId 학급 아이디
     * @param schoolYear    학년도
     * @return 조회된 신청 목록
     */
    public List<SchoolApply> findApproveSchoolApply(Long schoolClassId, Integer schoolYear) {
        return queryFactory
            .select(schoolApply)
            .from(schoolApply)
            .where(
                schoolApply.isDeleted.isFalse(),
                schoolApply.schoolClass.id.eq(schoolClassId),
                schoolApply.schoolYear.eq(schoolYear),
                schoolApply.isApproved.isTrue()
            )
            .orderBy(
                schoolApply.lastModifiedDate.desc()
            )
            .fetch();
    }
}
