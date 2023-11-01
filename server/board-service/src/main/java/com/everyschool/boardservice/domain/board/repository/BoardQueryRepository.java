package com.everyschool.boardservice.domain.board.repository;

import com.everyschool.boardservice.api.controller.board.response.NewFreeBoardResponse;
import com.everyschool.boardservice.domain.board.Category;
import com.everyschool.boardservice.domain.board.QBoard;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.boardservice.domain.board.QBoard.*;

@Repository
public class BoardQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BoardQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<NewFreeBoardResponse> findBySchoolId(Long schoolId, Category category) {
        return queryFactory
            .select(Projections.constructor(
                NewFreeBoardResponse.class,
                board.id,
                board.title
            ))
            .from(board)
            .where(
                board.schoolId.eq(schoolId),
                board.categoryId.eq(category.getCode())
            )
            .orderBy(board.createdDate.desc())
            .limit(5)
            .fetch();
    }
}
