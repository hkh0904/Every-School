package com.everyschool.chatservice.api.service.chatroom;

import com.everyschool.chatservice.IntegrationTestSupport;
import com.everyschool.chatservice.api.client.SchoolServiceClient;
import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.SchoolClassInfo;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.controller.chat.response.CreateChatRoomResponse;
import com.everyschool.chatservice.api.service.chatroom.dto.CreateChatRoomDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

class ChatRoomServiceTest extends IntegrationTestSupport {

    @Autowired
    private ChatRoomService chatRoomService;

    @MockBean
    private UserServiceClient userServiceClient;
    @MockBean
    private SchoolServiceClient schoolServiceClient;

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

        given(userServiceClient.searchUserInfo("jwt"))
                .willReturn(parent);
        given(userServiceClient.searchUserInfoByUserKey("opponentUserKey"))
                .willReturn(teacher);
        given(userServiceClient.searchChildName(1L, 1L))
                .willReturn("임우택");
        given(schoolServiceClient.searchSchoolClassInfo(1L))
                .willReturn(schoolClassInfo);

        CreateChatRoomDto dto = CreateChatRoomDto.builder()
                .loginUserToken("jwt")
                .opponentUserKey("opponentUserKey")
                .relation("1학년 2반 오연주 선생님")
                .schoolClassId(1L)
                .build();

        //when
        CreateChatRoomResponse response = chatRoomService.createChatRoom(dto);

        //then
        assertThat(response.getRoomId()).isPositive();
        assertThat(response.getRoomTitle()).isEqualTo("1학년 2반 오연주 선생님");
        assertThat(response.getUserName()).isEqualTo("오연주");

    }
}