package com.everyschool.chatservice.docs.chat;

import com.everyschool.chatservice.api.controller.chat.ChatController;
import com.everyschool.chatservice.api.controller.chat.request.CreateChatRoomRequest;
import com.everyschool.chatservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatControllerDocsTest extends RestDocsSupport {

    @DisplayName("채팅방 생성 API")
    @Test
    void createChatRoom() throws Exception {
        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
            .userKey(UUID.randomUUID().toString())
            .type("T")
            .build();

        mockMvc.perform(
                post("/chat-service/chat-room")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-chat-room",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userKey").type(JsonFieldType.STRING)
                        .optional()
                        .description("로그인 유저 키"),
                    fieldWithPath("type").type(JsonFieldType.STRING)
                        .optional()
                        .description("로그인 유저 타입")
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
                    fieldWithPath("data.roomTitle").type(JsonFieldType.STRING)
                        .description("채팅방 제목")
                )
            ));
    }

    @DisplayName("채팅방 목록 조회 API")
    @Test
    void searchChatRoomList() throws Exception {

        mockMvc.perform(
                get("/chat-service/chat-room/{userKey}", UUID.randomUUID().toString())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-chat-room-list",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터"),
                    fieldWithPath("data[].roomId").type(JsonFieldType.NUMBER)
                        .description("채팅방 PK"),
                    fieldWithPath("data[].roomTitle").type(JsonFieldType.STRING)
                        .description("채팅방 제목"),
                    fieldWithPath("data[].lastMessage").type(JsonFieldType.STRING)
                        .description("채팅방 마지막 메세지"),
                    fieldWithPath("data[].updateTime").type(JsonFieldType.ARRAY)
                        .description("채팅방 마지막 대화 시간"),
                    fieldWithPath("data[].unreadMessageNum").type(JsonFieldType.NUMBER)
                        .description("읽지 않은 메세지 수")
                )
            ));
    }

    @DisplayName("채팅 전송 API")
    @Test
    void sendMessage() throws Exception {

        mockMvc.perform(
                post("/chat-service/chat-room/{chatRoomId}", 1L)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("send-chat",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER)
                        .description("응답 데이터(채팅 PK)")
                )
            ));
    }

    @DisplayName("채팅 조회 API")
    @Test
    void searchChat() throws Exception {

        mockMvc.perform(
                get("/chat-service/chat-room/{chatRoomId}", 1L)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-chat",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터"),
                    fieldWithPath("data[].chatId").type(JsonFieldType.NUMBER)
                        .description("채팅 PK"),
                    fieldWithPath("data[].mine").type(JsonFieldType.BOOLEAN)
                        .description("로그인 한 유저가 보낸 채팅인지"),
                    fieldWithPath("data[].content").type(JsonFieldType.STRING)
                        .description("채팅 내용"),
                    fieldWithPath("data[].sendTime").type(JsonFieldType.STRING)
                        .description("전송 시간")
                )
            ));
    }

    @Override
    protected Object initController() {
        return new ChatController();
    }
}
