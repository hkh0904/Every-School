package com.everyschool.boardservice.domain.board.repository;

import com.everyschool.boardservice.api.app.controller.board.response.BoardResponse;
import com.everyschool.boardservice.api.app.controller.board.response.NewCommunicationResponse;
import com.everyschool.boardservice.api.app.controller.board.response.NewFreeBoardResponse;
import com.everyschool.boardservice.api.app.controller.board.response.NewNoticeResponse;
import com.everyschool.boardservice.domain.board.Category;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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

    public List<NewFreeBoardResponse> findNewFreeBoardBySchoolId(Long schoolId, Category category) {
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

    public List<NewNoticeResponse> findNewNoticeBySchoolId(Long schoolId, Category category) {
        return queryFactory
                .select(Projections.constructor(
                        NewNoticeResponse.class,
                        board.id,
                        board.title,
                        board.createdDate
                ))
                .from(board)
                .where(
                        board.schoolId.eq(schoolId),
                        board.categoryId.eq(category.getCode())
                )
                .orderBy(board.createdDate.desc())
                .limit(4)
                .fetch();
    }

    public List<NewCommunicationResponse> findNewCommunicationBySchoolId(Long schoolId, Category category) {
        return queryFactory
                .select(Projections.constructor(
                        NewCommunicationResponse.class,
                        board.id,
                        board.title,
                        board.createdDate
                ))
                .from(board)
                .where(
                        board.schoolId.eq(schoolId),
                        board.categoryId.eq(category.getCode())
                )
                .orderBy(board.createdDate.desc())
                .limit(3)
                .fetch();
    }

    public Slice<BoardResponse> findBoardBySchoolId(Long schoolId, Category category, Pageable pageable) {
        List<BoardResponse> content = queryFactory
                .select(Projections.constructor(
                        BoardResponse.class,
                        board.id,
                        board.title,
                        board.content,
                        board.commentCount,
                        board.scrapCount,
                        board.createdDate
                ))
                .from(board)
                .where(
                        board.schoolId.eq(schoolId),
                        board.categoryId.eq(category.getCode())
                )
                .orderBy(board.createdDate.desc())
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
}
