package com.everyschool.chatservice.domain.chatroom.repository;

import com.everyschool.chatservice.api.controller.chat.response.ChatRoomListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.chatservice.domain.chat.QChat.chat;
import static com.everyschool.chatservice.domain.chatroom.QChatRoom.chatRoom;
import static com.everyschool.chatservice.domain.chatroomuser.QChatRoomUser.chatRoomUser;

@Repository
public class ChatRoomQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ChatRoomQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ChatRoomListResponse> findChatRooms(Long loginUserId) {
        return queryFactory.select(Projections.constructor(ChatRoomListResponse.class,
                        chatRoom.id,
                        chatRoomUser.chatRoomTitle,
                        chat.content,
                        chat.createdDate,
                        chatRoomUser.unreadCount
                ))
                .from(chatRoomUser)
                .join(chatRoomUser.chatRoom,chatRoom)
                .join(chatRoom, chat.chatRoom).on()
                .where(chatRoomUser.userId.eq(loginUserId),
                        chat.)
                .fetch();
    }
}
