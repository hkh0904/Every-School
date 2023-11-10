package com.everyschool.callservice.docs.donotdisturb;

import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.controller.donotdisturb.DoNotDisturbQueryController;
import com.everyschool.callservice.api.controller.donotdisturb.response.DoNotDisturbResponse;
import com.everyschool.callservice.api.service.donotdisturb.DoNotDisturbQueryService;
import com.everyschool.callservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

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

public class DoNotDisturbQueryControllerDocsTest extends RestDocsSupport {

    private final UserServiceClient userServiceClient = mock(UserServiceClient.class);
    private final DoNotDisturbQueryService doNotDisturbQueryService = mock(DoNotDisturbQueryService.class);

    @Override
    protected Object initController() {
        return new DoNotDisturbQueryController(doNotDisturbQueryService);
    }

    @DisplayName("가장 최근에 등록한 내 금지 목록 조회 API")
    @Test
    void searchDoNotDisturb() throws Exception {

        DoNotDisturbResponse response = DoNotDisturbResponse.builder()
                .startTime(LocalDateTime.now().minusDays(1))
                .endTime(LocalDateTime.now().minusHours(23))
                .isActivate(false)
                .build();

        given(doNotDisturbQueryService.searchMyDoNotDisturb(anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get("/call-service/v1/do-not-disturbs/")
                                .header("Authorization", "Bearer Access Token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-do-not-disturb",
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
                                fieldWithPath("data.startTime").type(JsonFieldType.ARRAY)
                                        .description("시작 시간"),
                                fieldWithPath("data.endTime").type(JsonFieldType.ARRAY)
                                        .description("끝 나는 시간"),
                                fieldWithPath("data.isActivate").type(JsonFieldType.BOOLEAN)
                                        .description("활성화 상태")
                        )
                ));
    }
}
