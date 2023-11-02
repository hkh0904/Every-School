package com.everyschool.chatservice.docs.chat;

import com.everyschool.chatservice.api.controller.chat.ChatQueryController;
import com.everyschool.chatservice.api.controller.chat.response.ChatRoomListResponse;
import com.everyschool.chatservice.api.service.chatroom.ChatRoomQueryService;
import com.everyschool.chatservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatQueryControllerDocsTest extends RestDocsSupport {

    @DisplayName("채팅방 목록 조회 API")
    @Test
    void searchChatRoomList() throws Exception {

        ChatRoomListResponse response1 = createChatRoomListResponse(1L, "1학년 2반 하현상 선생님", "아이패드 에어4", LocalDateTime.of(2023, 10, 10, 10, 10), 3);

        ChatRoomListResponse response2 = createChatRoomListResponse(2L, "1학년 5반 임동규 선생님", "아이패드 프로", LocalDateTime.of(2023, 10, 10, 12, 10), 0);

        List<ChatRoomListResponse> responses = List.of(response1, response2);

        BDDMockito.given(chatRoomQueryService.searchChatRooms(anyString()))
                .willReturn(responses);

        mockMvc.perform(
                        get("/chat-service/v1/chat-room")
                                .header("Authorization", "jwt")
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

//    @DisplayName("채팅 전송 API")
//    @Test
//    void sendMessage() throws Exception {
//
//        mockMvc.perform(
//                        post("/chat-service/v1/chat-room/{chatRoomId}", 1L)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("send-chat",
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("code").type(JsonFieldType.NUMBER)
//                                        .description("코드"),
//                                fieldWithPath("status").type(JsonFieldType.STRING)
//                                        .description("상태"),
//                                fieldWithPath("message").type(JsonFieldType.STRING)
//                                        .description("메시지"),
//                                fieldWithPath("data").type(JsonFieldType.NUMBER)
//                                        .description("응답 데이터(채팅 PK)")
//                        )
//                ));
//    }

//    @DisplayName("채팅 조회 API")
//    @Test
//    void searchChat() throws Exception {
//
//        mockMvc.perform(
//                        get("/chat-service/v1/chat-room/{chatRoomId}", 1L)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("search-chat",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("code").type(JsonFieldType.NUMBER)
//                                        .description("코드"),
//                                fieldWithPath("status").type(JsonFieldType.STRING)
//                                        .description("상태"),
//                                fieldWithPath("message").type(JsonFieldType.STRING)
//                                        .description("메시지"),
//                                fieldWithPath("data").type(JsonFieldType.ARRAY)
//                                        .description("응답 데이터"),
//                                fieldWithPath("data[].chatId").type(JsonFieldType.NUMBER)
//                                        .description("채팅 PK"),
//                                fieldWithPath("data[].mine").type(JsonFieldType.BOOLEAN)
//                                        .description("로그인 한 유저가 보낸 채팅인지"),
//                                fieldWithPath("data[].content").type(JsonFieldType.STRING)
//                                        .description("채팅 내용"),
//                                fieldWithPath("data[].sendTime").type(JsonFieldType.STRING)
//                                        .description("전송 시간")
//                        )
//                ));
//    }

    private static ChatRoomListResponse createChatRoomListResponse(long roomId, String roomTitle, String lastMessage, LocalDateTime updateTime, int unreadMessageNum) {
        return ChatRoomListResponse.builder()
                .roomId(roomId)
                .roomTitle(roomTitle)
                .lastMessage(lastMessage)
                .updateTime(updateTime)
                .unreadMessageNum(unreadMessageNum)
                .build();
    }

    private final ChatRoomQueryService chatRoomQueryService = mock(ChatRoomQueryService.class);

    @Override
    protected Object initController() {
        return new ChatQueryController(chatRoomQueryService);
    }
}
