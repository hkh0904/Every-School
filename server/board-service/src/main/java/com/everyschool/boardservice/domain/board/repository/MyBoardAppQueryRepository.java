package com.everyschool.boardservice.domain.board.repository;

import com.everyschool.boardservice.api.app.controller.board.response.BoardResponse;
import com.everyschool.boardservice.domain.board.QComment;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.boardservice.domain.board.QBoard.board;
import static com.everyschool.boardservice.domain.board.QComment.*;

@Repository
@Slf4j
public class MyBoardAppQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MyBoardAppQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    public List<BoardResponse> findMyBoardsByUserId(Long userId, int category) {
        log.debug("[Repository] 나의 게시물 목록 조회.");
        return queryFactory
            .select(
                Projections.constructor(
                    BoardResponse.class,
                    board.id,
                    board.title,
                    board.content,
                    board.commentCount,
                    board.scrapCount,
                    board.createdDate
                )
            )
            .from(board)
            .where(
                board.isDeleted.isFalse(),
                board.userId.eq(userId),
                board.categoryId.eq(category)
            )
            .orderBy(board.lastModifiedDate.desc())
            .fetch();
    }

    public List<BoardResponse> findCommentBoardByUserId(Long userId) {
        return queryFactory
            .select(
                Projections.constructor(
                    BoardResponse.class,
                    comment.board.id,
                    comment.board.title,
                    comment.board.content,
                    comment.board.commentCount,
                    comment.board.createdDate
                )
            )
            .from(comment)
            .join(comment.board, board)
            .where(
                comment.isDeleted.isFalse(),
                comment.userId.eq(userId)
            )
            .orderBy(comment.lastModifiedDate.desc())
            .fetch();
    }
}
