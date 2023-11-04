package com.everyschool.consultservice.docs.consult;

import com.everyschool.consultservice.api.controller.consult.ConsultWebController;
import com.everyschool.consultservice.api.controller.consult.request.FinishConsultRequest;
import com.everyschool.consultservice.api.controller.consult.request.RejectConsultRequest;
import com.everyschool.consultservice.api.controller.consult.response.ApproveConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.FinishConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.RejectConsultResponse;
import com.everyschool.consultservice.api.service.consult.ConsultService;
import com.everyschool.consultservice.docs.RestDocsSupport;
import com.everyschool.consultservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
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

public class ConsultWebControllerDocsTest extends RestDocsSupport {

    private final ConsultService consultService = mock(ConsultService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private final String URL = "/consult-service/v1/web/{schoolYear}/schools/{schoolId}/consults";

    @Override
    protected Object initController() {
        return new ConsultWebController(consultService, tokenUtils);
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
                patch(URL + "/{consultId}/approve", 2023, 21617, 1L)
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

    @DisplayName("[교직원] 상담 완료 API")
    @Test
    void finishConsult() throws Exception {
        FinishConsultRequest request = FinishConsultRequest.builder()
            .resultContent("학습 지도를 하였습니다.")
            .build();

        FinishConsultResponse response = FinishConsultResponse.builder()
            .consultId(1L)
            .typeId(CALL.getCode())
            .progressStatusId(FINISH.getCode())
            .consultDateTime(LocalDateTime.now())
            .resultContent("학습 지도를 하였습니다.")
            .build();

        given(consultService.finishConsult(anyLong(), anyString()))
            .willReturn(response);

        mockMvc.perform(
                patch(URL + "/{consultId}/finish", 2023, 21617, 1L)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("finish-consult",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("resultContent").type(JsonFieldType.STRING)
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
                        .description("상담 id"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("상담 유형"),
                    fieldWithPath("data.progressStatus").type(JsonFieldType.STRING)
                        .description("상담 상태"),
                    fieldWithPath("data.resultContent").type(JsonFieldType.STRING)
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
                patch(URL + "/{consultId}/reject", 2023, 21617, 1L)
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
