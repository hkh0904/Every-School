package com.everyschool.chatservice.domain.chatroomuser.repository;

import com.everyschool.chatservice.IntegrationTestSupport;
import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import com.everyschool.chatservice.domain.chatroom.repository.ChatRoomRepository;
import com.everyschool.chatservice.domain.chatroomuser.ChatRoomUser;
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
        chatRoomUserRepository.save(ChatRoomUser.builder()
                .chatRoomTitle("1학년 2반 홍경환(부)")
                .socketTopic(String.valueOf(chatRoom.getId()))
                .userId(1L)
                .isAlarm(true)
                .unreadCount(0)
                .chatRoom(chatRoom)
                .build());

        //when
        Optional<ChatRoomUser> chatRoomUser1 = chatRoomUserQueryRepository.findChatRoomUserByRoomIdAndUserId(chatRoom.getId(), 1L);
        Optional<ChatRoomUser> chatRoomUser2 = chatRoomUserQueryRepository.findChatRoomUserByRoomIdAndUserId(5L, 1L);


        //then
        assertThat(chatRoomUser1).isPresent();
        assertThat(chatRoomUser1.get().getChatRoomTitle()).isEqualTo("1학년 2반 홍경환(부)");
        assertThat(chatRoomUser2).isEmpty();
    }

    private ChatRoom saveChatRoom() {
        return chatRoomRepository.save(ChatRoom.builder()
                .build());
    }
}