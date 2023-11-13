package com.everyschool.chatservice.domain.chat.repository;

import com.everyschool.chatservice.api.controller.chat.response.ChatReviewResponse;
import com.everyschool.chatservice.api.controller.chat.response.WarningChatReviewResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.everyschool.chatservice.domain.chat.QChatReview.chatReview;
import static com.everyschool.chatservice.domain.chatroomuser.QChatRoomUser.chatRoomUser;

@Repository
@Slf4j
public class ChatReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ChatReviewQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ChatReviewResponse> searchReviewChatDate(LocalDate date) {
        return queryFactory.select(Projections.constructor(ChatReviewResponse.class,
                        chatReview.title,
                        chatReview.chatRoom.id
                ))
                .from(chatReview)
                .where(
                        chatReview.chatDate.eq(date),
                        chatReview.isDeleted.eq(false))
                .orderBy(chatReview.title.desc())
                .fetch();
    }

    public List<WarningChatReviewResponse> searchReviewChatList(Long userId) {
        List<Long> roomIds = queryFactory.select(chatRoomUser.chatRoom.id)
                .from(chatRoomUser)
                .where(chatRoomUser.userId.eq(userId))
                .fetch();
        if (roomIds.size() == 0) {
            return null;
        }


        return queryFactory.select(Projections.constructor(WarningChatReviewResponse.class,
                        chatReview.id,
                        chatReview.chatRoom.id,
                        chatReview.chatDate))
                .from(chatReview)
                .where(chatReview.chatRoom.id.in(roomIds))
                .fetch();

    }
}
