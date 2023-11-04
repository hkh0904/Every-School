package com.everyschool.consultservice.docs.consult;

import com.everyschool.consultservice.api.controller.consult.ConsultQueryController;
import com.everyschool.consultservice.api.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.controller.consult.response.WebConsultResponse;
import com.everyschool.consultservice.api.service.consult.ConsultQueryService;
import com.everyschool.consultservice.docs.RestDocsSupport;
import com.everyschool.consultservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.everyschool.consultservice.domain.consult.ConsultType.*;
import static com.everyschool.consultservice.domain.consult.ProgressStatus.FINISH;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultQueryControllerDocsTest extends RestDocsSupport {

    private final ConsultQueryService consultQueryService = mock(ConsultQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);

    @Override
    protected Object initController() {
        return new ConsultQueryController(consultQueryService, tokenUtils);
    }

    @DisplayName("[교직원] 상담 목록 조회 API")
    @Test
    void searchConsults() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());
        WebConsultResponse response1 = createWaitConsultResponse(1L, "10301 하예솔 학생", "하도영 아버님", LocalDateTime.of(2023, 11, 4, 14, 0));
        WebConsultResponse response2 = createWaitConsultResponse(2L, "10301 하예솔 학생", "박연진 어머님", LocalDateTime.of(2023, 11, 4, 15, 0));

        given(consultQueryService.searchConsults(anyString(), anyInt(), anyInt()))
            .willReturn(List.of(response1, response2));

        mockMvc.perform(
            get("/consult-service/v1/web/{schoolYear}/schools/{schoolId}/consults", 2023, 21617)
                .header("Authorization", "Bearer Access Token")
                .param("status", "5001")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-consults",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("status")
                        .description("상담 진행 상태 코드")
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
                    fieldWithPath("data.count").type(JsonFieldType.NUMBER)
                        .description("상담 확인 대기 중인 신청 수"),
                    fieldWithPath("data.content[].consultId").type(JsonFieldType.NUMBER)
                        .description("상담 id"),
                    fieldWithPath("data.content[].type").type(JsonFieldType.STRING)
                        .description("상담 유형"),
                    fieldWithPath("data.content[].studentInfo").type(JsonFieldType.STRING)
                        .description("상담 학생 정보"),
                    fieldWithPath("data.content[].parentInfo").type(JsonFieldType.STRING)
                        .description("상담 학부모 정보"),
                    fieldWithPath("data.content[].consultDate").type(JsonFieldType.ARRAY)
                        .description("상담 일시")
                )
            ));
    }

    @DisplayName("[교직원] 상담 상세 조회 API")
    @Test
    void searchConsult() throws Exception {
        ConsultDetailResponse response = ConsultDetailResponse.builder()
            .consultId(1L)
            .schoolYear(2023)
            .typeId(CALL.getCode())
            .statusId(FINISH.getCode())
            .title("10301 하예솔 학생 박연진 어머님")
            .message("우리 예솔이가 아나운서 되고 싶다고 하네요.")
            .resultContent("어머님이 아나운서 선배로서 조언을 많이 해주시면 될 것 같습니다.")
            .rejectedReason("")
            .consultDateTime(LocalDateTime.of(2023, 10, 12, 14, 0))
            .createdDate(LocalDateTime.of(2023, 10, 1, 13, 27))
            .build();

        given(consultQueryService.searchConsult(anyLong()))
            .willReturn(response);

        mockMvc.perform(
            get("/consult-service/v1/web/{schoolYear}/schools/{schoolId}/consults/{consultId}", 2023, 21617, 1)
                .header("Authorization", "Bearer Access Token")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-consult",
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
                    fieldWithPath("data.schoolYear").type(JsonFieldType.NUMBER)
                        .description("상담 학년도"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("상담 유형"),
                    fieldWithPath("data.status").type(JsonFieldType.STRING)
                        .description("상담 상태"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("상담 제목"),
                    fieldWithPath("data.message").type(JsonFieldType.STRING)
                        .description("상담 메세지"),
                    fieldWithPath("data.resultContent").type(JsonFieldType.STRING)
                        .description("상담 결과 내용"),
                    fieldWithPath("data.rejectedReason").type(JsonFieldType.STRING)
                        .description("상담 거절 사유"),
                    fieldWithPath("data.consultDateTime").type(JsonFieldType.ARRAY)
                        .description("상담 일시"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("상담 등록 일시")
                )
            ));
    }

    private WebConsultResponse createWaitConsultResponse(Long consultId, String studentInfo, String parentInfo, LocalDateTime consultDate) {
        return WebConsultResponse.builder()
            .consultId(consultId)
            .typeId(VISIT.getCode())
            .studentInfo(studentInfo)
            .parentInfo(parentInfo)
            .consultDate(consultDate)
            .build();
    }
}
