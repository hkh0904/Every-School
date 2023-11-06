package com.everyschool.callservice.docs.call;

import com.everyschool.callservice.api.controller.usercall.UserCallQueryController;
import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.everyschool.callservice.api.service.call.UserCallQueryService;
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

public class UserCallQueryControllerDocsTest extends RestDocsSupport {
    private final UserCallQueryService userCallQueryService = mock(UserCallQueryService.class);

    @Override
    protected Object initController() {
        return new UserCallQueryController(userCallQueryService);
    }

    @DisplayName("내 통화 내역 조회 API")
    @Test
    void searchMyCalls() throws Exception {
        UserCallResponse r1 = UserCallResponse.builder()
                .userCallId(1L)
                .senderName("신성주")
                .receiverName("임우택 선생님")
                .sender("O")
                .startDateTime(LocalDateTime.now().minusHours(10))
                .endDateTime(LocalDateTime.now().minusHours(9))
                .isBad(false)
                .build();

        UserCallResponse r2 = UserCallResponse.builder()
                .userCallId(2L)
                .senderName("신성주")
                .receiverName("임우택 선생님")
                .sender("O")
                .startDateTime(LocalDateTime.now().minusHours(10))
                .endDateTime(LocalDateTime.now().minusHours(9))
                .isBad(false)
                .build();

        List<UserCallResponse> rList = new ArrayList<>();
        rList.add(r1);
        rList.add(r2);

        given(userCallQueryService.searchMyCalls(anyString()))
                .willReturn(rList);

        mockMvc.perform(
                        get("/call-service/v1/calls/{userKey}", UUID.randomUUID().toString())
                                .header("Authorization", "Bearer Access Token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-userCalls",
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
                                fieldWithPath("data[].userCallId").type(JsonFieldType.NUMBER)
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
                                fieldWithPath("data[].isBad").type(JsonFieldType.BOOLEAN)
                                        .description("악성 민원 여부")
                        )
                ));
    }
}
