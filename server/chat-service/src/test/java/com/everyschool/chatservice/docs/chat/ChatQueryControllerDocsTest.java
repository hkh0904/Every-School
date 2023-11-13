package com.everyschool.chatservice.docs.chat;

import com.everyschool.chatservice.api.controller.chat.ChatQueryController;
import com.everyschool.chatservice.api.controller.chat.response.ChatResponse;
import com.everyschool.chatservice.api.controller.chat.response.WarningChat;
import com.everyschool.chatservice.api.controller.chat.response.WarningChatResponse;
import com.everyschool.chatservice.api.controller.chat.response.WarningChatReviewResponse;
import com.everyschool.chatservice.api.service.chat.ChatQueryService;
import com.everyschool.chatservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.everyschool.chatservice.domain.chat.ChatStatus.PLANE;
import static com.everyschool.chatservice.domain.chat.ChatStatus.WARNING;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatQueryControllerDocsTest extends RestDocsSupport {

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
                                fieldWithPath("data[].sendTime").type(JsonFieldType.ARRAY)
                                        .description("전송 시간")
                        )
                ));
    }

    @DisplayName("문제 채팅 목록 조회 API")
    @Test
    void searchReviewChatList() throws Exception {

        WarningChatReviewResponse response1 = createWarningChatReviewResponse(1L, 1L, 11);
        WarningChatReviewResponse response2 = createWarningChatReviewResponse(2L, 2L, 9);
        WarningChatReviewResponse response3 = createWarningChatReviewResponse(3L, 1L, 10);

        List<WarningChatReviewResponse> responses = List.of(response1, response2, response3);

        BDDMockito.given(chatQueryService.searchReviewChatList(anyString()))
                .willReturn(responses);

        mockMvc.perform(get("/chat-service/v1/chat-review")
                        .header("Authorization", "jwt")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-warning-list",
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
                                fieldWithPath("data[].chatReviewId").type(JsonFieldType.NUMBER)
                                        .description("채팅 검토 PK"),
                                fieldWithPath("data[].chatRoomId").type(JsonFieldType.NUMBER)
                                        .description("채팅방 Id"),
                                fieldWithPath("data[].chatDate").type(JsonFieldType.ARRAY)
                                        .description("문제 채팅 날짜")
                        )
                ));
    }

    @DisplayName("문제 채팅 상세 조회 API")
    @Test
    void searchReviewChat() throws Exception {

        WarningChat response1 = createWarningChat(1L, false, "방망이 안주면 옷 더럽게 한다", "별 이유아닌 이유로 협박함", WARNING.getText());
        WarningChat response2 = createWarningChat(2L, true, "너 진짜 나쁜아이구나", "", PLANE.getText());
        WarningChat response3 = createWarningChat(3L, false, "오빤 키도 작으면서", "키공격함", WARNING.getText());
        WarningChat response4 = createWarningChat(4L, true, "반말 쓰지마!", "", PLANE.getText());
        WarningChat response5 = createWarningChat(5L, false, "키도 작으면서요", "킹받게함", WARNING.getText());
        WarningChat response6 = createWarningChat(6L, true, "장난하니?", "", PLANE.getText());
        WarningChat response7 = createWarningChat(7L, true, "이쁘면 다냐?", "혼내는척 예쁘다고 고급스킬 썼음", WARNING.getText());
        WarningChat response8 = createWarningChat(8L, false, "나 이뻐서 다야", "", PLANE.getText());
        WarningChat response9 = createWarningChat(9L, true, "그래 너 다 해라!", "", PLANE.getText());

        List<WarningChat> chats = List.of(response1,
                response2,
                response3,
                response4,
                response5,
                response6,
                response7,
                response8,
                response9);

        WarningChatResponse response = WarningChatResponse.builder()
                .chatList(chats)
                .title("1학년 1반 선생님과 방망이의 대화")
                .build();


        BDDMockito.given(chatQueryService.searchReviewChat(anyLong(), anyLong(), any(), anyString()))
                .willReturn(response);

        mockMvc.perform(get("/chat-service/v1/chat-review/{chatRoomId}/{reviewId}", 1L, 1L)
                        .header("Authorization", "jwt")
                        .param("date", "2023-11-01")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-warning-detail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING)
                                        .description("채팅방 이름"),
                                fieldWithPath("data.chatList").type(JsonFieldType.ARRAY)
                                        .description("채팅방 Id"),
                                fieldWithPath("data.chatList[].chatId").type(JsonFieldType.NUMBER)
                                        .description("채팅 Id"),
                                fieldWithPath("data.chatList[].teacherSend").type(JsonFieldType.BOOLEAN)
                                        .description("선생님 전송 여부"),
                                fieldWithPath("data.chatList[].content").type(JsonFieldType.STRING)
                                        .description("채팅 내용"),
                                fieldWithPath("data.chatList[].sendTime").type(JsonFieldType.ARRAY)
                                        .description("채팅 전송 시간"),
                                fieldWithPath("data.chatList[].chatStatus").type(JsonFieldType.STRING)
                                        .description("채팅 상태"),
                                fieldWithPath("data.chatList[].reason").type(JsonFieldType.STRING)
                                        .description("악성 판단 근거")
                        )
                ));
    }

    private static WarningChat createWarningChat(long chatId, boolean teacherSend, String content, String reason, String chatStatus) {
        return WarningChat.builder()
                .chatId(chatId)
                .teacherSend(teacherSend)
                .content(content)
                .sendTime(LocalDateTime.of(2023, 11, 1, 10, 10, 10))
                .chatStatus(chatStatus)
                .reason(reason)
                .build();
    }

    private static WarningChatReviewResponse createWarningChatReviewResponse(long chatReviewId, long chatRoomId, int day) {
        return WarningChatReviewResponse.builder()
                .chatReviewId(chatReviewId)
                .chatRoomId(chatRoomId)
                .chatDate(LocalDate.of(2023, 11, day))
                .build();
    }

    private static ChatResponse createChatResponse(long chatId, boolean isMine, String content, int minute) {
        return ChatResponse.builder()
                .chatId(chatId)
                .isMine(isMine)
                .content(content)
                .sendTime(LocalDateTime.of(2023, 11, 1, 10, minute))
                .build();
    }

    private final ChatQueryService chatQueryService = mock(ChatQueryService.class);

    @Override
    protected Object initController() {
        return new ChatQueryController(chatQueryService);
    }
}
