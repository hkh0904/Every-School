package com.everyschool.chatservice.api.service.chatroom;

import com.everyschool.chatservice.IntegrationTestSupport;
import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.controller.chat.response.CreateChatRoomResponse;
import com.everyschool.chatservice.domain.chatroom.repository.ChatRoomRepository;
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
    private ChatRoomRepository chatRoomRepository;

    @MockBean
    private UserServiceClient userServiceClient;

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

        given(userServiceClient.searchUserInfo("jwt"))
            .willReturn(parent);
        given(userServiceClient.searchUserInfoByUserKey("userKey"))
            .willReturn(teacher);
        //when
        CreateChatRoomResponse response = chatRoomService.createChatRoom();

        //then
        assertThat(response.getRoomId()).isPositive();
        assertThat(response.getRoomTitle()).isEqualTo("오연주 선생님");

    }
}