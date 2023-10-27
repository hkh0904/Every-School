package com.everyschool.callservice.docs.call;

import com.everyschool.callservice.api.controller.call.CallController;
import com.everyschool.callservice.api.controller.call.request.CreateCallRequest;
import com.everyschool.callservice.api.service.call.CallService;
import com.everyschool.callservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CallControllerDocsTest extends RestDocsSupport {

    private CallService callService = mock(CallService.class);

    @Override
    protected Object initController() {
        return new CallController(callService);
    }

    @DisplayName("선생님 통화 종료시 통화 내역 저장 API")
    @Test
    void createCallInfo() throws Exception {
        CreateCallRequest request = CreateCallRequest.builder()
                .otherUserKey(UUID.randomUUID().toString())
                .sender("O")
                .startDateTime(LocalDateTime.now().minusHours(12))
                .endDateTime(LocalDateTime.now().minusHours(11))
                .uploadFileName("이예리 폭언 논란 원본")
                .storeFileName("이예리 폭언 논란 원본")
                .build();

        mockMvc.perform(
                        post("/call-service/v1/calls/")
                                .header("Authorization", "Bearer Access Token")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-call-info",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("otherUserKey").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("선생님과 통화 하는 상대방"),
                                fieldWithPath("sender").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("발신자"),
                                fieldWithPath("startDateTime").type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("통화 시작 시간"),
                                fieldWithPath("endDateTime").type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("통화 종료 시간"),
                                fieldWithPath("uploadFileName").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("업로드 할 파일명"),
                                fieldWithPath("storeFileName").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("저장된 파일 명")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.STRING)
                                        .description("응답 데이터")
                        )
                ));
    }
}
