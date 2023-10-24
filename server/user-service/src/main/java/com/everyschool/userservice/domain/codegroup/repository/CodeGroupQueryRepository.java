package com.everyschool.userservice.domain.codegroup.repository;

import com.everyschool.userservice.api.controller.codegroup.response.CodeGroupResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.everyschool.userservice.domain.codegroup.QCodeGroup.codeGroup;

@Repository
public class CodeGroupQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CodeGroupQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public boolean existGroupName(String groupName) {
        Integer result = queryFactory
            .select(codeGroup.id)
            .from(codeGroup)
            .where(codeGroup.groupName.eq(groupName))
            .fetchFirst();
        return result != null;
    }

    public List<CodeGroupResponse> findAll() {
        return queryFactory
            .select(Projections.constructor(CodeGroupResponse.class,
                codeGroup.id,
                codeGroup.groupName,
                codeGroup.isDeleted
            ))
            .from(codeGroup)
            .orderBy(codeGroup.createdDate.desc())
            .fetch();
    }
}
