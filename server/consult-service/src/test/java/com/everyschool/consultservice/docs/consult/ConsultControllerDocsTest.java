package com.everyschool.consultservice.docs.consult;

import com.everyschool.consultservice.api.controller.consult.ConsultController;
import com.everyschool.consultservice.api.controller.consult.request.CreateConsultRequest;
import com.everyschool.consultservice.api.controller.consult.request.CreateConsultScheduleRequest;
import com.everyschool.consultservice.api.controller.consult.request.RejectConsultRequest;
import com.everyschool.consultservice.api.controller.consult.response.ApproveConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.CreateConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.RejectConsultResponse;
import com.everyschool.consultservice.api.service.consult.ConsultService;
import com.everyschool.consultservice.api.service.consult.dto.CreateConsultDto;
import com.everyschool.consultservice.docs.RestDocsSupport;
import com.everyschool.consultservice.domain.consult.ConsultType;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import com.everyschool.consultservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static com.everyschool.consultservice.domain.consult.ConsultType.*;
import static com.everyschool.consultservice.domain.consult.ProgressStatus.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultControllerDocsTest extends RestDocsSupport {

    private final ConsultService consultService = mock(ConsultService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);

    @Override
    protected Object initController() {
        return new ConsultController(consultService, tokenUtils);
    }

    @DisplayName("상담 등록 API")
    @Test
    void createConsult() throws Exception {
        CreateConsultRequest request = CreateConsultRequest.builder()
            .consultDateTime(LocalDateTime.now())
            .message("리온이가 너무 귀여워요!")
            .typeId(VISIT.getCode())
            .teacherKey(generateUUID())
            .studentKey(generateUUID())
            .build();

        given(tokenUtils.getUserKey())
            .willReturn(generateUUID());

        CreateConsultResponse response = CreateConsultResponse.builder()
            .consultId(1L)
            .typeId(2001)
            .teacher("1학년 3반 임우택 선생님")
            .applicant("1학년 3반 이리온(모) 이예리")
            .consultDateTime(LocalDateTime.now())
            .message("리온이가 너무 귀여워요!")
            .build();

        given(consultService.createConsult(anyString(), anyLong(), any(CreateConsultDto.class)))
            .willReturn(response);

        mockMvc.perform(
                post("/consult-service/v1/schools/{schoolId}/consults/{schoolYear}", 1L, 2023)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-consult",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("consultDateTime").type(JsonFieldType.ARRAY)
                        .optional()
                        .description("상담 일시"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .optional()
                        .description("상담 내용"),
                    fieldWithPath("typeId").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("상담 유형"),
                    fieldWithPath("teacherKey").type(JsonFieldType.STRING)
                        .optional()
                        .description("교직원 식별키"),
                    fieldWithPath("studentKey").type(JsonFieldType.STRING)
                        .optional()
                        .description("학생(자녀) 식별키")
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
                    fieldWithPath("data.teacher").type(JsonFieldType.STRING)
                        .description("교직원 정보"),
                    fieldWithPath("data.applicant").type(JsonFieldType.STRING)
                        .description("신청자 정보"),
                    fieldWithPath("data.consultDateTime").type(JsonFieldType.ARRAY)
                        .description("상담 일시"),
                    fieldWithPath("data.message").type(JsonFieldType.STRING)
                        .description("상담 메세지")
                )
            ));
    }

    @DisplayName("[교직원] 상담 승인 API")
    @Test
    void approveConsult() throws Exception {
        ApproveConsultResponse response = ApproveConsultResponse.builder()
            .consultId(1L)
            .typeId(VISIT.getCode())
            .progressStatusId(RESERVATION.getCode())
            .consultDateTime(LocalDateTime.now())
            .build();

        given(consultService.approveConsult(anyLong()))
            .willReturn(response);

        mockMvc.perform(
            patch("/consult-service/v1/schools/{schoolId}/consults/{schoolYear}/approve/{consultId}", 1L, 2023, 1L)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("approve-consult",
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
                    fieldWithPath("data.consultId").type(JsonFieldType.NUMBER)
                        .description("상담 id"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("상담 유형"),
                    fieldWithPath("data.progressStatus").type(JsonFieldType.STRING)
                        .description("상담 상태"),
                    fieldWithPath("data.consultDateTime").type(JsonFieldType.ARRAY)
                        .description("상담 일시")
                )
            ));
    }

    @DisplayName("[교직원] 상담 거절 API")
    @Test
    void rejectConsult() throws Exception {
        RejectConsultRequest request = RejectConsultRequest.builder()
            .rejectedReason("교직원 연수 일정으로 상담이 불가합니다.")
            .build();

        RejectConsultResponse response = RejectConsultResponse.builder()
            .consultId(1L)
            .typeId(VISIT.getCode())
            .progressStatusId(RESERVATION.getCode())
            .consultDateTime(LocalDateTime.now())
            .rejectedReason("교직원 연수 일정으로 상담이 불가합니다.")
            .build();

        given(consultService.rejectConsult(anyLong(), anyString()))
            .willReturn(response);

        mockMvc.perform(
                patch("/consult-service/v1/schools/{schoolId}/consults/{schoolYear}/reject/{consultId}", 1L, 2023, 1L)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("reject-consult",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("rejectedReason").type(JsonFieldType.STRING)
                        .description("상담 거절 사유")
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
                        .description("상담 id"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("상담 유형"),
                    fieldWithPath("data.progressStatus").type(JsonFieldType.STRING)
                        .description("상담 상태"),
                    fieldWithPath("data.consultDateTime").type(JsonFieldType.ARRAY)
                        .description("상담 일시"),
                    fieldWithPath("data.rejectedReason").type(JsonFieldType.STRING)
                        .description("상담 거절 사유")
                )
            ));
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
