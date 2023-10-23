package com.everyschool.userservice.domain.codedetail.repository;

import com.everyschool.userservice.domain.codedetail.QCodeDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
}
