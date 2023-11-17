package com.everyschool.boardservice.domain.board.repository;

import com.everyschool.boardservice.api.app.controller.scrap.response.MyScrapResponse;
import com.everyschool.boardservice.domain.board.Scrap;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.boardservice.domain.board.QBoard.board;
import static com.everyschool.boardservice.domain.board.QScrap.scrap;

@Repository
public class ScrapQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ScrapQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    public Slice<MyScrapResponse> findByUserId(Long userId, Pageable pageable) {
        List<MyScrapResponse> content = queryFactory
                .select(
                        Projections.constructor(
                                MyScrapResponse.class,
                                scrap.board.id,
                                scrap.board.categoryId,
                                scrap.board.title,
                                scrap.board.content,
                                scrap.board.commentCount,
                                scrap.board.createdDate
                        )
                )
                .from(scrap)
                .join(scrap.board, board)
                .where(scrap.userId.eq(userId))
                .orderBy(scrap.lastModifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    public Scrap findByBoardAndUserId(Long boardId, Long userId) {
        return queryFactory
                .select(scrap)
                .from(scrap)
                .where(scrap.userId.eq(userId)
                        , scrap.board.id.eq(boardId))
                .fetchOne();
    }
}
