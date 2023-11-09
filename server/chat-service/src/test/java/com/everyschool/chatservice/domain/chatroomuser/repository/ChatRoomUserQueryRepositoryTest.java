package com.everyschool.chatservice.domain.chatroomuser.repository;

import com.everyschool.chatservice.IntegrationTestSupport;
import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import com.everyschool.chatservice.domain.chatroom.repository.ChatRoomRepository;
import com.everyschool.chatservice.domain.chatroomuser.ChatRoomUser;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ChatRoomUserQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ChatRoomUserQueryRepository chatRoomUserQueryRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatRoomUserRepository chatRoomUserRepository;

    @DisplayName("채팅방과 유저 아이디로 채팅방 유저 가져오기")
    @Test
    void findChatRoomUserByRoomIdAndUserId() {
        //given
        ChatRoom chatRoom = saveChatRoom();
        saveChatRoomUser(chatRoom, 1L);

        //when
        Optional<ChatRoomUser> chatRoomUser1 = chatRoomUserQueryRepository.findChatRoomUserByRoomIdAndUserId(chatRoom.getId(), 1L);
        Optional<ChatRoomUser> chatRoomUser2 = chatRoomUserQueryRepository.findChatRoomUserByRoomIdAndUserId(5L, 1L);


        //then
        assertThat(chatRoomUser1).isPresent();
        assertThat(chatRoomUser1.get().getChatRoomTitle()).isEqualTo("1학년 2반 홍경환(부)");
        assertThat(chatRoomUser2).isEmpty();
    }

    @DisplayName("유저 2명으로 채팅방 찾기")
    @Test
    void findChatRoomIdByTwoUserId() {
        //given
        ChatRoom chatRoom = saveChatRoom();
        ChatRoomUser chatRoomUser1 = saveChatRoomUser(chatRoom, 1L);
        ChatRoomUser chatRoomUser2 = saveChatRoomUser(chatRoom, 2L);

        //when
        Optional<Long> find = chatRoomUserQueryRepository.findChatRoomIdByTwoUserId(1L, 2L);

        //then
        assertThat(find).isPresent();
    }

    @NotNull
    private ChatRoomUser saveChatRoomUser(ChatRoom chatRoom, long userId) {
        return chatRoomUserRepository.save(ChatRoomUser.builder()
                .chatRoomTitle("1학년 2반 홍경환(부)")
                .childName("신짱구")
                .userId(userId)
                .opponentUserType("F")
                .isAlarm(true)
                .unreadCount(0)
                .chatRoom(chatRoom)
                .build());
    }

    private ChatRoom saveChatRoom() {
        return chatRoomRepository.save(ChatRoom.builder()
                .build());
    }
}