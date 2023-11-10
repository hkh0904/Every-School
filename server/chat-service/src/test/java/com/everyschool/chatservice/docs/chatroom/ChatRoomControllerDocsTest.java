package com.everyschool.chatservice.docs.chatroom;

import com.everyschool.chatservice.api.controller.chatroom.ChatRoomController;
import com.everyschool.chatservice.api.controller.chatroom.request.CreateChatRoomRequest;
import com.everyschool.chatservice.api.controller.chatroom.response.CreateChatRoomResponse;
import com.everyschool.chatservice.api.service.chatroom.ChatRoomService;
import com.everyschool.chatservice.api.service.chatroom.dto.CreateChatRoomDto;
import com.everyschool.chatservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatRoomControllerDocsTest extends RestDocsSupport {

    @DisplayName("채팅방 생성 API")
    @Test
    void createChatRoom() throws Exception {
        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
                .opponentUserKey("opponentUserKey")
                .opponentUserName("봉미선")
                .opponentUserType("M")
                .loginUserType(1000)
                .schoolClassId(1L)
                .build();

        CreateChatRoomResponse response = CreateChatRoomResponse.builder()
                .roomId(1L)
                .opponentUserName("봉미선")
                .opponentUserType('M')
                .opponentUsersChildName("신짱구")
                .build();

        BDDMockito.given(chatRoomService.createChatRoom(any(CreateChatRoomDto.class)))
                .willReturn(response);

        mockMvc.perform(
                        post("/chat-service/v1/chat-rooms")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "jwt")
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-chat-room",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("opponentUserKey").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("상대방 유저 키"),
                                fieldWithPath("opponentUserName").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("상대방 유저 이름"),
                                fieldWithPath("opponentUserType").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("상대방 유저 타입"),
                                fieldWithPath("loginUserType").type(JsonFieldType.NUMBER)
                                        .optional()
                                        .description("로그인 유저 타입"),
                                fieldWithPath("schoolClassId").type(JsonFieldType.NUMBER)
                                        .description("학급 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.roomId").type(JsonFieldType.NUMBER)
                                        .description("채팅방 ID"),
                                fieldWithPath("data.opponentUserName").type(JsonFieldType.STRING)
                                        .description("상대 유저 이름"),
                                fieldWithPath("data.opponentUserType").type(JsonFieldType.STRING)
                                        .description("상대방 타입"),
                                fieldWithPath("data.opponentUsersChildName").type(JsonFieldType.STRING)
                                        .description("상대방이 부모일 때 자녀 이름")
                        )
                ));
    }

    private final ChatRoomService chatRoomService = mock(ChatRoomService.class);

    @Override
    protected Object initController() {
        return new ChatRoomController(chatRoomService);
    }
}
