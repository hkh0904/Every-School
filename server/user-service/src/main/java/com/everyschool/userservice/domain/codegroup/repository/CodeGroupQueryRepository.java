package com.everyschool.userservice.domain.codegroup.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CodeGroupQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CodeGroupQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public boolean existGroupName(String groupName) {
        return false;
    }
}
