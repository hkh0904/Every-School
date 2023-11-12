package com.everyschool.chatservice.docs.chatroom;

import com.everyschool.chatservice.api.controller.chat.response.ChatRoomListResponse;
import com.everyschool.chatservice.api.controller.chatroom.ChatRoomQueryController;
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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatRoomQueryControllerDocsTest extends RestDocsSupport {

    @DisplayName("채팅방 목록 조회 API")
    @Test
    void searchChatRoomList() throws Exception {

        ChatRoomListResponse response1 = createChatRoomListResponse(1L, "봉미선", "짱구 엄마입니다~", LocalDateTime.of(2023, 10, 10, 10, 10), 3, "M", "신짱구");

        ChatRoomListResponse response2 = createChatRoomListResponse(2L, "신짱아", "짱아입니다~", LocalDateTime.of(2023, 10, 10, 12, 10), 0, "S", "");

        List<ChatRoomListResponse> responses = List.of(response1, response2);

        BDDMockito.given(chatRoomQueryService.searchChatRooms(anyString()))
                .willReturn(responses);

        mockMvc.perform(
                        get("/chat-service/v1/chat-rooms")
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
                                fieldWithPath("data[].opponentUserName").type(JsonFieldType.STRING)
                                        .description("상대방 이름"),
                                fieldWithPath("data[].opponentUserType").type(JsonFieldType.STRING)
                                        .description("상대방 회원 타입"),
                                fieldWithPath("data[].opponentUserChildName").type(JsonFieldType.STRING)
                                        .description("상대방 자녀 이름"),
                                fieldWithPath("data[].lastMessage").type(JsonFieldType.STRING)
                                        .description("채팅방 마지막 메세지"),
                                fieldWithPath("data[].updateTime").type(JsonFieldType.ARRAY)
                                        .description("채팅방 마지막 대화 시간"),
                                fieldWithPath("data[].unreadMessageNum").type(JsonFieldType.NUMBER)
                                        .description("읽지 않은 메세지 수")
                        )
                ));
    }

    private static ChatRoomListResponse createChatRoomListResponse(long roomId, String roomTitle, String lastMessage, LocalDateTime updateTime, int unreadMessageNum, String opponentUserType, String opponentUserChildName) {
        return ChatRoomListResponse.builder()
                .roomId(roomId)
                .opponentUserName(roomTitle)
                .opponentUserType(opponentUserType)
                .opponentUserChildName(opponentUserChildName)
                .lastMessage(lastMessage)
                .updateTime(updateTime)
                .unreadMessageNum(unreadMessageNum)
                .build();
    }

    private final ChatRoomQueryService chatRoomQueryService = mock(ChatRoomQueryService.class);

    @Override
    protected Object initController() {
        return new ChatRoomQueryController(chatRoomQueryService);
    }
}
