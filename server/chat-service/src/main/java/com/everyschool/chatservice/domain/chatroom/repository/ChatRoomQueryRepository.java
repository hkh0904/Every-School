package com.everyschool.chatservice.domain.chatroom.repository;

import com.everyschool.chatservice.api.controller.chat.response.ChatRoomListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.chatservice.domain.chat.QChat.chat;
import static com.everyschool.chatservice.domain.chatroomuser.QChatRoomUser.chatRoomUser;

@Repository
@Slf4j
public class ChatRoomQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ChatRoomQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ChatRoomListResponse> findChatRooms(Long loginUserId) {
        return queryFactory.select(Projections.constructor(ChatRoomListResponse.class,
                        chat.chatRoom.id,
                        chatRoomUser.chatRoomTitle,
                        chat.content,
                        chat.createdDate,
                        chatRoomUser.unreadCount
                ))
                .from(chatRoomUser)
                .innerJoin(chat).on(chat.chatRoom.id.eq(chatRoomUser.chatRoom.id))
                .where(
                        chatRoomUser.userId.eq(loginUserId),
                        chat.createdDate.eq(
                                JPAExpressions.select(chat.createdDate.max())
                                        .from(chat)
                                        .where(chatRoomUser.chatRoom.eq(chat.chatRoom))))
                .orderBy(chatRoomUser.unreadCount.desc(), chat.createdDate.desc())
                .fetch();
    }
}
