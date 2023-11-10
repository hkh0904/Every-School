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

    /**
     * 채팅방 Id로 2명 유저 찾기
     *
     * @param roomId
     */
    public List<ChatRoomUser> findChatRoomUsersByChatRoomId(Long roomId) {
        return queryFactory
                .select(chatRoomUser)
                .from(chatRoomUser)
                .where(chatRoomUser.chatRoom.id.eq(roomId))
                .fetch();
    }

    /**
     * 내가 속한 그 채팅방 정보 가져오기
     *
     * @param roomId
     * @param userId
     * @return
     */
    public Optional<ChatRoomUser> findChatRoomUserByRoomIdAndUserId(Long roomId, Long userId) {
        return Optional.ofNullable(queryFactory
                .select(chatRoomUser)
                .from(chatRoomUser)
                .where(chatRoomUser.userId.eq(userId),
                        chatRoomUser.chatRoom.id.eq(roomId)).fetchOne());
    }

    /**
     * 채팅방 다른 유저 가져오기
     *
     * @param chatRoomId
     * @param senderUserId
     * @return
     */
    public Optional<Long> findOpponentUserId(Long chatRoomId, Long senderUserId) {
        return Optional.ofNullable(queryFactory
                .select(chatRoomUser.userId)
                .from(chatRoomUser)
                .join(chatRoom).on(chatRoom.id.eq(chatRoomId))
                .where(chatRoomUser.userId.ne(senderUserId))
                .fetchOne());
    }

    /**
     * 두 유저 Id로 채팅방 찾기
     *
     * @param user1Id
     * @param user2Id
     * @return
     */
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
