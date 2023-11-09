package com.everyschool.consultservice.docs.app.consult;

import com.everyschool.consultservice.api.app.controller.consult.ConsultAppQueryController;
import com.everyschool.consultservice.api.app.service.consult.ConsultAppQueryService;
import com.everyschool.consultservice.docs.RestDocsSupport;
import com.everyschool.consultservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultAppQueryControllerDocsTest extends RestDocsSupport {

    private final ConsultAppQueryService consultAppQueryService = mock(ConsultAppQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/consult-service/v1/app/{schoolYear}/schools/{schoolId}/consults";

    @Override
    protected Object initController() {
        return new ConsultAppQueryController(consultAppQueryService, tokenUtils);
    }

    @DisplayName("학부모 상담 내역 조회 API")
    @Test
    void searchConsultsByParent() throws Exception {
        mockMvc.perform(
                get(BASE_URL, 2023, 100000)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-consults-by-parent",
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
                    fieldWithPath("data[].consultId").type(JsonFieldType.ARRAY)
                        .description("상담 아이디"),
                    fieldWithPath("data[].type").type(JsonFieldType.ARRAY)
                        .description("상담 유형"),
                    fieldWithPath("data[].status").type(JsonFieldType.ARRAY)
                        .description("상담 진행 상태"),
                    fieldWithPath("data[].info").type(JsonFieldType.ARRAY)
                        .description("상담 교직원 정보"),
                    fieldWithPath("data[].consultDateTime").type(JsonFieldType.ARRAY)
                        .description("상담 일시")
                )
            ));
    }

}
