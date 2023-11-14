package com.everyschool.schoolservice.domain.schoolapply.repository;

import com.everyschool.schoolservice.domain.schoolapply.QSchoolApply;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.everyschool.schoolservice.domain.schoolapply.QSchoolApply.*;

@Repository
public class SchoolApplyAppQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SchoolApplyAppQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public boolean isExistApply(Long parentId) {
        List<Long> content = queryFactory
            .select(schoolApply.id)
            .from(schoolApply)
            .where(
                schoolApply.isDeleted.isFalse(),
                schoolApply.isApproved.isFalse(),
                schoolApply.parentId.eq(parentId),
                schoolApply.rejectedReason.isNull()
            )
            .fetch();

        return !content.isEmpty();
    }
}
