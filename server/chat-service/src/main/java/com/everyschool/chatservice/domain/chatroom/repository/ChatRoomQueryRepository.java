package com.everyschool.chatservice.domain.chatroom.repository;

import com.everyschool.chatservice.api.controller.chat.response.ChatRoomListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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
                        chatRoomUser.chatRoom.id,
                        chatRoomUser.chatRoomTitle,
                        chatRoomUser.lastContent,
                        chatRoomUser.lastModifiedDate,
                        chatRoomUser.unreadCount
                ))
                .from(chatRoomUser)
                .where(
                        chatRoomUser.userId.eq(loginUserId), chatRoomUser.isDeleted.eq(false))
                .orderBy(chatRoomUser.lastModifiedDate.desc())
                .fetch();
    }
}
