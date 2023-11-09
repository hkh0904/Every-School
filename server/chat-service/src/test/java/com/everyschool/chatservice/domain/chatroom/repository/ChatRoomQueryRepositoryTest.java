package com.everyschool.chatservice.domain.chatroom.repository;

import com.everyschool.chatservice.IntegrationTestSupport;
import com.everyschool.chatservice.api.controller.chat.response.ChatRoomListResponse;
import com.everyschool.chatservice.api.service.SequenceGeneratorService;
import com.everyschool.chatservice.domain.chat.Chat;
import com.everyschool.chatservice.domain.chat.ChatStatus;
import com.everyschool.chatservice.domain.chat.repository.ChatRepository;
import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import com.everyschool.chatservice.domain.chatroomuser.ChatRoomUser;
import com.everyschool.chatservice.domain.chatroomuser.repository.ChatRoomUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ChatRoomQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ChatRoomQueryRepository chatRoomQueryRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ChatRoomUserRepository chatRoomUserRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @AfterEach
    void tearDown() {
        chatRepository.deleteAll();
    }

    @DisplayName("[Repository] 로그인 한 유저가 속한 채팅방 목록 가져오기")
    @Test
    void findChatRooms() {
        //given
        ChatRoom savedChatRoom1 = chatRoomRepository.save(ChatRoom.builder().build());
        ChatRoomUser loginRoomUser1 = createChatRoomUser(savedChatRoom1, "봉미선", "신짱구", 101L, "M", 3);
        ChatRoomUser opponentRoomUser1 = createChatRoomUser(savedChatRoom1, "채성아", null, 103L, "T", 0);
        Chat loginRoomUser1SentMessage = createSentMessage(savedChatRoom1, loginRoomUser1, opponentRoomUser1, "채성아 선생님이 봉미선한테 선톡함");
        Chat opponentRoomUser1SentMessage = createSentMessage(savedChatRoom1, opponentRoomUser1, loginRoomUser1, "봉미선이 채성아 선생님한테 톡한게 출력돼야함.");

        ChatRoom savedChatRoom2 = chatRoomRepository.save(ChatRoom.builder().build());
        ChatRoomUser loginRoomUser2 = createChatRoomUser(savedChatRoom2, "신짱아", null, 101L, "S", 0);
        ChatRoomUser opponentRoomUser2 = createChatRoomUser(savedChatRoom2, "채성아", null, 102L, "T", 3);
        Chat opponentRoomUser2SentMessage = createSentMessage(savedChatRoom2, opponentRoomUser2, loginRoomUser2, "짱아가 채성아 선생님한테 선톡");
        Chat loginRoomUser2SentMessage = createSentMessage(savedChatRoom2, loginRoomUser2, opponentRoomUser2, "채성아 선생님이 짱아한테 톡보낸거 출력됨");

        //when
        List<ChatRoomListResponse> chatRooms = chatRoomQueryRepository.findChatRooms(loginRoomUser1.getUserId());
        for (ChatRoomListResponse chatRoom : chatRooms) {
            System.out.println("채팅방 id : " + chatRoom.getRoomId());
            System.out.println("채팅방 제목(상대 이름) : " + chatRoom.getOpponentUserName());
            System.out.println("채팅방 마지막메세지 : " + chatRoom.getLastMessage());
            System.out.println();
        }

        //then
        assertThat(chatRooms.size()).isEqualTo(2);
    }

    private Chat createSentMessage(ChatRoom savedChatRoom, ChatRoomUser sender, ChatRoomUser receiver, String message) {
        sender.updateUpdateChat(message);
        receiver.updateUpdateChat(message);
        return chatRepository.save(Chat.builder()
                .id(sequenceGeneratorService.generateSequence(Chat.SEQUENCE_NAME))
                .userId(sender.getUserId())
                .content(message)
                .status(ChatStatus.PLANE.getCode())
                .chatRoomId(savedChatRoom.getId())
                .build());
    }

    private ChatRoomUser createChatRoomUser(ChatRoom savedChatRoom, String opponentUserName, String childName, long userId, String opponentUserType, int unreadCount) {
        return chatRoomUserRepository.save(ChatRoomUser.builder()
                .chatRoomTitle(opponentUserName)
                .childName(childName)
                .userId(userId)
                .opponentUserType(opponentUserType)
                .isAlarm(true)
                .unreadCount(unreadCount)
                .chatRoom(savedChatRoom)
                .build());
    }
}