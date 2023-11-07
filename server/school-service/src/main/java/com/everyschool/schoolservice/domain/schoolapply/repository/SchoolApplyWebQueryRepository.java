package com.everyschool.schoolservice.domain.schoolapply.repository;

import com.everyschool.schoolservice.domain.schoolapply.QSchoolApply;
import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.schoolservice.domain.schoolapply.QSchoolApply.*;

@Repository
public class SchoolApplyWebQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SchoolApplyWebQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

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
