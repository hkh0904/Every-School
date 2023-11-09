package com.everyschool.consultservice.docs.app.consult;

import com.everyschool.consultservice.api.app.controller.consult.ConsultAppQueryController;
import com.everyschool.consultservice.api.app.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.api.app.service.consult.ConsultAppQueryService;
import com.everyschool.consultservice.docs.RestDocsSupport;
import com.everyschool.consultservice.domain.consult.ConsultType;
import com.everyschool.consultservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.everyschool.consultservice.domain.consult.ProgressStatus.*;
import static org.mockito.BDDMockito.*;
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
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        ConsultResponse response3 = createConsultResponse(3L, FINISH.getCode(), "2학년 3반 이예리 선생님");
        ConsultResponse response2 = createConsultResponse(2L, REJECT.getCode(), "2학년 3반 이예리 선생님");
        ConsultResponse response1 = createConsultResponse(1L, WAIT.getCode(), "2학년 3반 이예리 선생님");

        given(consultAppQueryService.searchConsultsByParent(anyString(), anyInt()))
            .willReturn(List.of(response1, response2, response3));

        mockMvc.perform(
                get(BASE_URL + "/parent", 2023, 100000)
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
                    fieldWithPath("data[].consultId").type(JsonFieldType.NUMBER)
                        .description("상담 아이디"),
                    fieldWithPath("data[].type").type(JsonFieldType.STRING)
                        .description("상담 유형"),
                    fieldWithPath("data[].status").type(JsonFieldType.STRING)
                        .description("상담 진행 상태"),
                    fieldWithPath("data[].info").type(JsonFieldType.STRING)
                        .description("상담 교직원 정보"),
                    fieldWithPath("data[].consultDateTime").type(JsonFieldType.ARRAY)
                        .description("상담 일시")
                )
            ));
    }

    @DisplayName("교직원 상담 내역 조회 API")
    @Test
    void searchConsultsByTeacher() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        ConsultResponse response3 = createConsultResponse(3L, FINISH.getCode(), "2학년 3반 임우택 어머님");
        ConsultResponse response2 = createConsultResponse(2L, REJECT.getCode(), "2학년 3반 이예리 어머님");
        ConsultResponse response1 = createConsultResponse(1L, WAIT.getCode(), "2학년 3반 신성주 아버님");

        given(consultAppQueryService.searchConsultsByTeacher(anyString(), anyInt()))
            .willReturn(List.of(response1, response2, response3));

        mockMvc.perform(
                get(BASE_URL + "/teacher", 2023, 100000)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-consults-by-teacher",
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
                    fieldWithPath("data[].consultId").type(JsonFieldType.NUMBER)
                        .description("상담 아이디"),
                    fieldWithPath("data[].type").type(JsonFieldType.STRING)
                        .description("상담 유형"),
                    fieldWithPath("data[].status").type(JsonFieldType.STRING)
                        .description("상담 진행 상태"),
                    fieldWithPath("data[].info").type(JsonFieldType.STRING)
                        .description("상담 교직원 정보"),
                    fieldWithPath("data[].consultDateTime").type(JsonFieldType.ARRAY)
                        .description("상담 일시")
                )
            ));
    }

    private ConsultResponse createConsultResponse(Long consultId, int status, String info) {
        return ConsultResponse.builder()
            .consultId(consultId)
            .type(ConsultType.VISIT.getCode())
            .status(status)
            .info(info)
            .consultDateTime(LocalDateTime.now())
            .build();
    }
}
