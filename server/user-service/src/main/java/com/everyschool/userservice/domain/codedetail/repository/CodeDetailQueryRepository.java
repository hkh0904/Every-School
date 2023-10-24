package com.everyschool.userservice.domain.codedetail.repository;

import com.everyschool.userservice.api.controller.codedetail.respnse.CodeDetailResponse;
import com.everyschool.userservice.domain.codedetail.QCodeDetail;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.everyschool.userservice.domain.codedetail.QCodeDetail.codeDetail;

@Repository
public class CodeDetailQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CodeDetailQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public boolean existCodeName(int groupId, String codeName) {
        Integer result = queryFactory
            .select(codeDetail.id)
            .from(codeDetail)
            .where(
                codeDetail.group.id.eq(groupId),
                codeDetail.codeName.eq(codeName)
            )
            .fetchFirst();
        return result != null;
    }

    public List<CodeDetailResponse> findByGroupId(int groupId) {
        return queryFactory
            .select(Projections.constructor(CodeDetailResponse.class,
                codeDetail.id,
                codeDetail.codeName,
                codeDetail.isDeleted
            ))
            .from(codeDetail)
            .where(
                codeDetail.group.id.eq(groupId)
            )
            .fetch();
    }
}
