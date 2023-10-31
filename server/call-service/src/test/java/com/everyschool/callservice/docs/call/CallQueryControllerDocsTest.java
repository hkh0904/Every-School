package com.everyschool.callservice.docs.call;

import com.everyschool.callservice.api.controller.call.CallQueryController;
import com.everyschool.callservice.api.controller.call.response.CallResponse;
import com.everyschool.callservice.api.service.call.CallQueryService;
import com.everyschool.callservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CallQueryControllerDocsTest extends RestDocsSupport {
    private final CallQueryService callQueryService = mock(CallQueryService.class);

    @Override
    protected Object initController() {
        return new CallQueryController(callQueryService);
    }

    @DisplayName("내 통화 내역 조회 API")
    @Test
    void searchMyCalls() throws Exception {
        CallResponse r1 = CallResponse.builder()
                .callId(1L)
                .senderName("신성주")
                .receiverName("임우택 선생님")
                .sender("O")
                .startDateTime(LocalDateTime.now().minusHours(10))
                .endDateTime(LocalDateTime.now().minusHours(9))
                .uploadFileName("음성 파일")
                .storeFileName("음성 파일")
                .isBad(false)
                .build();

        CallResponse r2 = CallResponse.builder()
                .callId(2L)
                .senderName("신성주")
                .receiverName("임우택 선생님")
                .sender("O")
                .startDateTime(LocalDateTime.now().minusHours(10))
                .endDateTime(LocalDateTime.now().minusHours(9))
                .uploadFileName("")
                .storeFileName("")
                .isBad(false)
                .build();

        List<CallResponse> rList = new ArrayList<>();
        rList.add(r1);
        rList.add(r2);

        given(callQueryService.searchMyCalls(anyString()))
                .willReturn(rList);

        mockMvc.perform(
                        get("/call-service/v1/calls/{userKey}", UUID.randomUUID().toString())
                                .header("Authorization", "Bearer Access Token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-calls",
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
                                fieldWithPath("data[].callId").type(JsonFieldType.NUMBER)
                                        .description("통화 ID"),
                                fieldWithPath("data[].senderName").type(JsonFieldType.STRING)
                                        .description("발신자"),
                                fieldWithPath("data[].receiverName").type(JsonFieldType.STRING)
                                        .description("수신자"),
                                fieldWithPath("data[].sender").type(JsonFieldType.STRING)
                                        .description("발신한 사람(T: 선생님, O: 다른 유저)"),
                                fieldWithPath("data[].startDateTime").type(JsonFieldType.ARRAY)
                                        .description("통화 시작 시간"),
                                fieldWithPath("data[].endDateTime").type(JsonFieldType.ARRAY)
                                        .description("통화 종료 시간"),
                                fieldWithPath("data[].uploadFileName").type(JsonFieldType.STRING)
                                        .description("업로드 된 음성 파일"),
                                fieldWithPath("data[].storeFileName").type(JsonFieldType.STRING)
                                        .description("저장된 음성 파일"),
                                fieldWithPath("data[].isBad").type(JsonFieldType.BOOLEAN)
                                        .description("악성 민원 여부")
                        )
                ));
    }
}
