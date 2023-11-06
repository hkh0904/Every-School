package com.everyschool.chatservice.docs.chatroom;

import com.everyschool.chatservice.api.controller.chat.ChatQueryController;
import com.everyschool.chatservice.api.controller.chat.response.ChatResponse;
import com.everyschool.chatservice.api.service.chat.ChatQueryService;
import com.everyschool.chatservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatRoomQueryControllerDocsTest extends RestDocsSupport {

    @DisplayName("채팅 조회 API")
    @Test
    void searchChat() throws Exception {

        ChatResponse chat1 = createChatResponse(1L, true, "벌써 며칠째 전화도 없는 너", 1);
        ChatResponse chat2 = createChatResponse(2L, true, "얼마 후면 나의 생일이란 걸 아는지", 2);
        ChatResponse chat3 = createChatResponse(3L, true, "눈치도 없이 시간은 자꾸만 흘러가고", 3);
        ChatResponse chat4 = createChatResponse(4L, true, "난 미움보다 걱정스런 맘에", 4);
        ChatResponse chat5 = createChatResponse(5L, true, "무작정 찾아간 너의 골목 어귀에서", 5);
        ChatResponse chat6 = createChatResponse(6L, true, "생각지 못한 웃으며", 6);
        ChatResponse chat7 = createChatResponse(7L, true, "반기는 너를 봤어", 7);
        ChatResponse chat8 = createChatResponse(8L, false, "사실은 말야 나 많이 고민했어", 8);
        ChatResponse chat9 = createChatResponse(9L, false, "네게 아무것도 해줄 수 없는걸", 9);
        ChatResponse chat10 = createChatResponse(10L, false, "아주 많이 모자라도 가진 것 없어도", 10);
        ChatResponse chat11 = createChatResponse(11L, false, "이런 나라도 받아 줄래", 11);
        ChatResponse chat12 = createChatResponse(12L, true, "너를 위해서 너만을 위해서", 12);
        ChatResponse chat13 = createChatResponse(13L, false, "난 세상 모든 걸", 13);
        ChatResponse chat14 = createChatResponse(14L, false, "다 안겨 주지는 못하지만", 14);
        ChatResponse chat15 = createChatResponse(15L, true, "오직 너를 위한 내가 될게", 15);
        ChatResponse chat16 = createChatResponse(16L, true, "Is only for you", 16);
        ChatResponse chat17 = createChatResponse(17L, true, "just wanna be for you", 17);
        ChatResponse chat18 = createChatResponse(18L, false, "넌 그렇게 지금 모습 그대로", 18);
        ChatResponse chat19 = createChatResponse(19L, false, "내 곁에 있으면 돼", 19);
        ChatResponse chat20 = createChatResponse(20L, true, "난 다시 태어나도", 20);

        List<ChatResponse> responses = List.of(chat1, chat2, chat3, chat4, chat5, chat6, chat7, chat8, chat9, chat10, chat11, chat12, chat13, chat14, chat15, chat16, chat17, chat18, chat19, chat20);

        BDDMockito.given(chatQueryService.searchChat(anyLong(), anyLong(), anyString()))
                .willReturn(responses);

        mockMvc.perform(get("/chat-service/v1/chat-rooms/{chatRoomId}", 1L)
                        .header("Authorization", "jwt")
                        .param("idx", "1")
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

    private static ChatResponse createChatResponse(long chatId, boolean isMine, String content, int minute) {
        return ChatResponse.builder().chatId(chatId).isMine(isMine).content(content).sendTime(LocalDateTime.of(2023, 11, 1, 10, minute)).build();
    }

    private final ChatQueryService chatQueryService = mock(ChatQueryService.class);

    @Override
    protected Object initController() {
        return new ChatQueryController(chatQueryService);
    }
}
