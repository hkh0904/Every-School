package com.everyschool.chatservice.api.service.chat;

import com.everyschool.chatservice.IntegrationTestSupport;
import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.controller.chat.response.ChatResponse;
import com.everyschool.chatservice.api.service.SequenceGeneratorService;
import com.everyschool.chatservice.domain.chat.Chat;
import com.everyschool.chatservice.domain.chat.ChatStatus;
import com.everyschool.chatservice.domain.chat.repository.ChatRepository;
import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import com.everyschool.chatservice.domain.chatroom.repository.ChatRoomRepository;
import com.everyschool.chatservice.domain.chatroomuser.ChatRoomUser;
import com.everyschool.chatservice.domain.chatroomuser.repository.ChatRoomUserRepository;
import com.everyschool.chatservice.domain.mongo.repository.DatabaseSequenceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class ChatQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private ChatQueryService chatQueryService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatRoomUserRepository chatRoomUserRepository;
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private DatabaseSequenceRepository databaseSequenceRepository;

    @MockBean
    private UserServiceClient userServiceClient;

//    @AfterEach
//    void dataReset() {
//        chatRepository.deleteAll();
//        databaseSequenceRepository.deleteAll();
//    }

    @DisplayName("[Service] 채팅방 접속 시 채팅 목록 조회")
    @Test
    void searchChat() {
        //given
        ChatRoom savedChatRoom = chatRoomRepository.save(ChatRoom.builder().build());
        ChatRoomUser teacher = createChatRoomUser(savedChatRoom, "봉미선", "신짱구", 101L, "M", 3);
        ChatRoomUser parent = createChatRoomUser(savedChatRoom, "채성아", null, 103L, "T", 0);
        Chat chat1 = createSentMessage(savedChatRoom, teacher, parent, "올포유");
        Chat chat2 = createSentMessage(savedChatRoom, teacher, parent, "벌써 며칠째 전화도 없는 너");
        Chat chat3 = createSentMessage(savedChatRoom, teacher, parent, "얼마 후면 나의 생일이란 걸 아는지");
        Chat chat4 = createSentMessage(savedChatRoom, teacher, parent, "눈치도 없이 시간은 자꾸만 흘러가고");
        Chat chat5 = createSentMessage(savedChatRoom, teacher, parent, "난 미움보다 걱정스런 맘에");
        Chat chat6 = createSentMessage(savedChatRoom, teacher, parent, "무작정 찾아간 너의 골목 어귀에서");
        Chat chat7 = createSentMessage(savedChatRoom, teacher, parent, "생각지 못한 웃으며");
        Chat chat8 = createSentMessage(savedChatRoom, teacher, parent, "반기는 너를 봤어");
        Chat chat9 = createSentMessage(savedChatRoom, parent, teacher, "사실은 말야 나 많이 고민했어");
        Chat chat10 = createSentMessage(savedChatRoom, parent, teacher, "네게 아무것도 해줄 수 없는걸");
        Chat chat11 = createSentMessage(savedChatRoom, parent, teacher, "아주 많이 모자라도 가진 것 없어도");
        Chat chat12 = createSentMessage(savedChatRoom, parent, teacher, "이런 나라도 받아 줄래");
        Chat chat13 = createSentMessage(savedChatRoom, teacher, parent, "너를 위해서 너만을 위해서");
        Chat chat14 = createSentMessage(savedChatRoom, parent, teacher, "난 세상 모든 걸");
        Chat chat15 = createSentMessage(savedChatRoom, parent, teacher, "다 안겨 주지는 못하지만");
        Chat chat16 = createSentMessage(savedChatRoom, teacher, parent, "오직 너를 위한 내가 될게");
        Chat chat17 = createSentMessage(savedChatRoom, teacher, parent, "Is only for you");
        Chat chat18 = createSentMessage(savedChatRoom, teacher, parent, "just wanna be for you");
        Chat chat19 = createSentMessage(savedChatRoom, parent, teacher, "넌 그렇게 지금 모습 그대로");
        Chat chat20 = createSentMessage(savedChatRoom, parent, teacher, "내 곁에 있으면 돼");
        Chat chat21 = createSentMessage(savedChatRoom, teacher, parent, "난 다시 태어나도");
        Chat chat22 = createSentMessage(savedChatRoom, parent, teacher, "영원히 너만 바라볼게");

        UserInfo teacherInfo = UserInfo.builder()
                .userId(1L)
                .userType('T')
                .userName("서인국")
                .schoolClassId(1L)
                .build();
        given(userServiceClient.searchUserInfo("jwtToken"))
                .willReturn(teacherInfo);
        //when
        List<ChatResponse> responses = chatQueryService.searchChat(savedChatRoom.getId(), null, "jwtToken");
        //then
        assertThat(responses).hasSize(20);

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