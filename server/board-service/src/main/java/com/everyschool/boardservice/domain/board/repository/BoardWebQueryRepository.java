package com.everyschool.boardservice.domain.board.repository;

import com.everyschool.boardservice.domain.board.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.boardservice.domain.board.Category.*;
import static com.everyschool.boardservice.domain.board.QBoard.*;

@Repository
public class BoardWebQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BoardWebQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Board> findBySchoolYearAndSchoolId(Long schoolId) {
        return queryFactory
            .select(board)
            .from(board)
            .where(
                board.isDeleted.isFalse(),
                board.schoolId.eq(schoolId),
                board.categoryId.eq(COMMUNICATION.getCode())
            )
            .orderBy(board.createdDate.desc())
            .fetch();
    }
}
