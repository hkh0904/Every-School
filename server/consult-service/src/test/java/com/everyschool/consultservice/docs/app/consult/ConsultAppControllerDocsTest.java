package com.everyschool.consultservice.docs.app.consult;

import com.everyschool.consultservice.api.app.controller.consult.ConsultAppController;
import com.everyschool.consultservice.api.app.controller.consult.request.CreateConsultRequest;
import com.everyschool.consultservice.api.app.controller.consult.response.CreateConsultResponse;
import com.everyschool.consultservice.api.app.service.consult.ConsultAppService;
import com.everyschool.consultservice.docs.RestDocsSupport;
import com.everyschool.consultservice.domain.consult.ConsultType;
import com.everyschool.consultservice.utils.TokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultAppControllerDocsTest extends RestDocsSupport {

    private final ConsultAppService consultAppService = mock(ConsultAppService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/consult-service/v1/app/{schoolYear}/schools/{schoolId}/consults";

    @Override
    protected Object initController() {
        return new ConsultAppController(consultAppService, tokenUtils);
    }

    @DisplayName("상담 등록 API")
    @Test
    void createConsult() throws Exception {
        LocalDateTime consultDateTime = LocalDateTime.now();
        CreateConsultRequest request = CreateConsultRequest.builder()
            .teacherKey(UUID.randomUUID().toString())
            .studentKey(UUID.randomUUID().toString())
            .typeId(ConsultType.VISIT.getCode())
            .consultDateTime(consultDateTime)
            .message("우리 아이 진로에 대해서 상담하고 싶습니다.")
            .build();

        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        CreateConsultResponse response = CreateConsultResponse.builder()
            .consultId(1L)
            .type(ConsultType.VISIT.getCode())
            .teacherInfo("2학년 2반 정인재 선생님")
            .parentInfo("2학년 2반 3번 이리온 어머님")
            .consultDateTime(consultDateTime)
            .message("우리 아이 진로에 대해서 상담하고 싶습니다.")
            .build();

        given(consultAppService.createConsult(anyString(), anyInt(), anyLong(), any()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-consult",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("teacherKey").type(JsonFieldType.STRING)
                        .optional()
                        .description("교직원 식별키"),
                    fieldWithPath("studentKey").type(JsonFieldType.STRING)
                        .optional()
                        .description("학생(자녀) 식별키"),
                    fieldWithPath("typeId").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("상담 유형"),
                    fieldWithPath("consultDateTime").type(JsonFieldType.ARRAY)
                        .optional()
                        .description("상담 일시"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .optional()
                        .description("상담 내용")
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
                    fieldWithPath("data.consultId").type(JsonFieldType.NUMBER)
                        .description("상담 ID"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("상담 유형"),
                    fieldWithPath("data.teacherInfo").type(JsonFieldType.STRING)
                        .description("교직원 정보"),
                    fieldWithPath("data.parentInfo").type(JsonFieldType.STRING)
                        .description("학부모 정보"),
                    fieldWithPath("data.consultDateTime").type(JsonFieldType.ARRAY)
                        .description("상담 일시"),
                    fieldWithPath("data.message").type(JsonFieldType.STRING)
                        .description("상담 메세지")
                )
            ));
    }
}
