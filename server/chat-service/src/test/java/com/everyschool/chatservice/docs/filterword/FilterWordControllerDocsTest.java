package com.everyschool.chatservice.docs.filterword;

import com.everyschool.chatservice.api.controller.chat.request.ChatMessage;
import com.everyschool.chatservice.api.controller.filterword.FilterWordController;
import com.everyschool.chatservice.api.controller.filterword.request.CreateFilterWordRequest;
import com.everyschool.chatservice.api.controller.filterword.response.ChatFilterResponse;
import com.everyschool.chatservice.api.service.filterword.FilterWordService;
import com.everyschool.chatservice.api.service.filterword.dto.CreateFilterWordDto;
import com.everyschool.chatservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FilterWordControllerDocsTest extends RestDocsSupport {

    @DisplayName("채팅 필터링 단어 등록 API")
    @Test
    void createFilterWord() throws Exception {
        CreateFilterWordRequest request = CreateFilterWordRequest.builder()
                .word("비속어")
                .build();
        CreateFilterWordDto dto = CreateFilterWordDto.builder()
                .loginUserToken("loginUserToken")
                .word("비속어")
                .build();
        BDDMockito.given(filterWordService.createFilterWord(dto))
                .willReturn(1L);

        mockMvc.perform(
                        post("/chat-service/v1/filters")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "loginUserToken")
                ).andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-filter-word",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("word").type(JsonFieldType.STRING)
                                        .description("필터링 단어")
                                        .optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER)
                                        .description("응답 데이터(생성 필터 단어 ID)")
                        )
                ));
    }

    @DisplayName("채팅 필터링 API")
    @Test
    void checkMessageFilter() throws Exception {
        ChatMessage message = ChatMessage.builder()
                .chatRoomId(1L)
                .senderUserKey("senderUserKey")
                .message("어쩌고 저쩌고")
                .build();

        ChatFilterResponse response = ChatFilterResponse.builder()
                .isBad(false)
                .reason("")
                .build();

        BDDMockito.given(filterWordService.sendMessage(any()))
                .willReturn(response);

        mockMvc.perform(
                        post("/chat-service/v1/filters/chat")
                                .content(objectMapper.writeValueAsString(message))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andDo(document("check-message-filter",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("chatRoomId").type(JsonFieldType.NUMBER)
                                        .description("채팅방 Id")
                                        .optional(),
                                fieldWithPath("senderUserKey").type(JsonFieldType.STRING)
                                        .description("보낸 사람 UserKey")
                                        .optional(),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("보낸 채팅 내용")
                                        .optional()
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
                                fieldWithPath("data.isBad").type(JsonFieldType.BOOLEAN)
                                        .description("악성 채팅 여부"),
                                fieldWithPath("data.reason").type(JsonFieldType.STRING)
                                        .description("악성 채팅 판단 이유")
                        )
                ));

    }

    private final FilterWordService filterWordService = mock(FilterWordService.class);

    @Override
    protected Object initController() {
        return new FilterWordController(filterWordService);
    }
}


// TODO: 2023-11-01 이거 임시 커밋함 해야함