package com.everyschool.chatservice.domain.chatroomuser.repository;

import com.everyschool.chatservice.domain.chatroomuser.ChatRoomUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.everyschool.chatservice.domain.chatroom.QChatRoom.chatRoom;
import static com.everyschool.chatservice.domain.chatroomuser.QChatRoomUser.chatRoomUser;

@Repository
@Slf4j
public class ChatRoomUserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ChatRoomUserQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<ChatRoomUser> findChatRoomUserByRoomIdAndUserId(Long roomId, Long userId) {
        return Optional.ofNullable(queryFactory
                .select(chatRoomUser)
                .from(chatRoomUser)
                .where(chatRoomUser.userId.eq(userId),
                        chatRoomUser.chatRoom.id.eq(roomId)).fetchOne());
    }

    public Optional<Long> findOpponentUserId(Long chatRoomId, Long senderUserId) {
        return Optional.ofNullable(queryFactory
                .select(chatRoomUser.userId)
                .from(chatRoomUser)
                .join(chatRoom).on(chatRoom.id.eq(chatRoomId))
                .where(chatRoomUser.userId.ne(senderUserId))
                .fetchOne());
    }

    public Optional<Long> findChatRoomIdByTwoUserId(Long user1Id, Long user2Id) {
        List<Long> query = queryFactory.select(chatRoomUser.chatRoom.id)
                .from(chatRoomUser)
                .where(chatRoomUser.userId.eq(user2Id))
                .fetch();
        if (query.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(queryFactory
                .select(chatRoomUser.chatRoom.id)
                .from(chatRoomUser)
                .where(chatRoomUser.userId.eq(user1Id),
                        chatRoomUser.chatRoom.id.in(
                                query
                        )).fetchFirst());
    }
}
