package com.everyschool.consultservice.domain.consult.repository;

import com.everyschool.consultservice.domain.consult.Consult;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.consultservice.domain.consult.QConsult.consult;

@Repository
public class ConsultQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ConsultQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Consult> findByParentId(Long parentId) {
        return queryFactory
            .select(consult)
            .from(consult)
            .where(consult.parentId.eq(parentId))
            .fetch();
    }
}
