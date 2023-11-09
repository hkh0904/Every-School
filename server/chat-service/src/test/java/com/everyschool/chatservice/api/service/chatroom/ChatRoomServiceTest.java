package com.everyschool.chatservice.api.service.chatroom;

import com.everyschool.chatservice.IntegrationTestSupport;
import com.everyschool.chatservice.api.client.SchoolServiceClient;
import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.SchoolClassInfo;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.controller.chat.response.CreateChatRoomResponse;
import com.everyschool.chatservice.api.service.chatroom.dto.CreateChatRoomDto;
import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import com.everyschool.chatservice.domain.chatroom.repository.ChatRoomRepository;
import com.everyschool.chatservice.domain.chatroomuser.ChatRoomUser;
import com.everyschool.chatservice.domain.chatroomuser.repository.ChatRoomUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

class ChatRoomServiceTest extends IntegrationTestSupport {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomUserRepository chatRoomUserRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @MockBean
    private UserServiceClient userServiceClient;
    @MockBean
    private SchoolServiceClient schoolServiceClient;

    @DisplayName("[Service] 채팅방 생성시 기존 방 있으면 기존 방 반환")
    @Test
    void createChatRoomAlready() {
        //given
        UserInfo opponentUserInfo = UserInfo.builder()
                .userId(1L)
                .userType('M')
                .userName("신성주")
                .schoolClassId(1L)
                .build();

        UserInfo loginUserInfo = UserInfo.builder()
                .userId(2L)
                .userType('T')
                .userName("오연주")
                .schoolClassId(1L)
                .build();

        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder().build());

        ChatRoomUser opponentUserRoomUser = saveChatRoomUser(chatRoom, "오연주", "", 1L, "T");
        ChatRoomUser loginUserRoomUser = saveChatRoomUser(chatRoom, "신성주", "임우택", 2L, "M");

        SchoolClassInfo schoolClassInfo = SchoolClassInfo.builder()
                .schoolName("떡잎초")
                .schoolYear(2023)
                .grade(1)
                .classNum(2)
                .build();


        given(userServiceClient.searchUserInfo(anyString()))
                .willReturn(loginUserInfo);
        given(userServiceClient.searchUserInfoByUserKey(anyString()))
                .willReturn(opponentUserInfo);
        given(userServiceClient.searchChildName(anyLong(), anyLong()))
                .willReturn("임우택");

        CreateChatRoomDto dto = CreateChatRoomDto.builder()
                .loginUserToken("jwt")
                .opponentUserKey("opponentUserKey")
                .opponentUserName("신성주")
                .opponentUserType("M")
                .schoolClassId(1L)
                .loginUserType(1000)
                .build();

        //when
        CreateChatRoomResponse response = chatRoomService.createChatRoom(dto);

        //then
        assertThat(response.getRoomId()).isEqualTo(opponentUserRoomUser.getChatRoom().getId());
        assertThat(response.getOpponentUserName()).isEqualTo("신성주");
        assertThat(response.getOpponentUserType()).isEqualTo('M');
        assertThat(response.getOpponentUsersChildName()).isEqualTo("임우택");

    }

    private ChatRoomUser saveChatRoomUser(ChatRoom chatRoom, String opponentUserName, String opponentUserChildName, long userId, String opponentUserType) {
        ChatRoomUser chatRoomUser = ChatRoomUser.builder()
                .chatRoomTitle(opponentUserName)
                .childName(opponentUserChildName)
                .userId(userId)
                .opponentUserType(opponentUserType)
                .isAlarm(true)
                .unreadCount(0)
                .lastContent("")
                .chatRoom(chatRoom)
                .build();
        return chatRoomUserRepository.save(chatRoomUser);
    }

    @DisplayName("채팅방 생성")
    @Test
    void createChatRoom() {
        //given
        UserInfo parent = UserInfo.builder()
                .userId(1L)
                .userType('M')
                .userName("신성주")
                .schoolClassId(1L)
                .build();

        UserInfo teacher = UserInfo.builder()
                .userId(2L)
                .userType('T')
                .userName("오연주")
                .schoolClassId(1L)
                .build();
        SchoolClassInfo schoolClassInfo = SchoolClassInfo.builder()
                .schoolName("떡잎초")
                .schoolYear(2023)
                .grade(1)
                .classNum(2)
                .build();

        given(userServiceClient.searchUserInfo(anyString()))
                .willReturn(parent);
        given(userServiceClient.searchUserInfoByUserKey(anyString()))
                .willReturn(teacher);
        given(userServiceClient.searchChildName(anyLong(), anyLong()))
                .willReturn("임우택");
        given(schoolServiceClient.searchSchoolClassInfo(anyLong()))
                .willReturn(schoolClassInfo);

        CreateChatRoomDto dto = CreateChatRoomDto.builder()
                .loginUserToken("jwt")
                .opponentUserKey("opponentUserKey")
                .opponentUserName("오연주")
                .opponentUserType("T")
                .schoolClassId(1L)
                .loginUserType(1000)
                .build();

        //when
        CreateChatRoomResponse response = chatRoomService.createChatRoom(dto);

        //then
        assertThat(response.getRoomId()).isPositive();
        assertThat(response.getOpponentUserName()).isEqualTo("오연주");
        assertThat(response.getOpponentUserType()).isEqualTo('T');
    }
}