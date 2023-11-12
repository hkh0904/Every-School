package com.everyschool.reportservice.docs.app.report;

import com.everyschool.reportservice.api.app.controller.report.ReportAppQueryController;
import com.everyschool.reportservice.api.app.controller.report.response.ReportDetailResponse;
import com.everyschool.reportservice.api.app.controller.report.response.ReportResponse;
import com.everyschool.reportservice.api.app.service.report.ReportAppQueryService;
import com.everyschool.reportservice.docs.RestDocsSupport;
import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.everyschool.reportservice.domain.report.ReportType;
import com.everyschool.reportservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportAppQueryControllerDocsTest extends RestDocsSupport {

    private final ReportAppQueryService reportAppQueryService = mock(ReportAppQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/report-service/v1/app/{schoolYear}/schools/{schoolId}/reports";

    @Override
    protected Object initController() {
        return new ReportAppQueryController(reportAppQueryService, tokenUtils);
    }

    @DisplayName("신고 목록 조회 API")
    @Test
    void searchReports() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        ReportResponse response = ReportResponse.builder()
            .reportId(1L)
            .typeId(ReportType.VIOLENCE.getCode())
            .statusId(ProgressStatus.REGISTER.getCode())
            .createdDate(LocalDateTime.now())
            .build();

        given(reportAppQueryService.searchReports(anyString(), anyInt(), anyLong()))
            .willReturn(List.of(response));

        mockMvc.perform(
                get(BASE_URL, 2023, 100000)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-reports",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName("Authorization")
                        .description("Bearer Access Token")
                ),
                pathParameters(
                    parameterWithName("schoolYear")
                        .description("학년도"),
                    parameterWithName("schoolId")
                        .description("학교 아이디")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터"),
                    fieldWithPath("data[].reportId").type(JsonFieldType.NUMBER)
                        .description("신고 id"),
                    fieldWithPath("data[].type").type(JsonFieldType.STRING)
                        .description("신고 유형"),
                    fieldWithPath("data[].status").type(JsonFieldType.STRING)
                        .description("신고 상태"),
                    fieldWithPath("data[].createdDate").type(JsonFieldType.ARRAY)
                        .description("신고 일시")
                )
            ));
    }

    @DisplayName("신고 상세 조회 API")
    @Test
    void searchReport() throws Exception {
        ReportDetailResponse response = ReportDetailResponse.builder()
            .reportId(1L)
            .schoolYear(22023)
            .type(ReportType.VIOLENCE.getCode())
            .status(ProgressStatus.FINISH.getCode())
            .witness("2학년 2반 13번 이예리")
            .who("신성주")
            .when("2023-11-09 12시경")
            .where("C221")
            .what("자꾸 놀려요.")
            .how(null)
            .why(null)
            .description("자꾸 괴롭히고 놀려요.")
            .result("신성주 학생에게 주의를 주었습니다.")
            .files(List.of("kakao.jpg"))
            .build();

        given(reportAppQueryService.searchReport(anyLong()))
            .willReturn(response);

        mockMvc.perform(
                get(BASE_URL + "/{reportId}", 2023, 100000, 1)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-report",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName("Authorization")
                        .description("Bearer Access Token")
                ),
                pathParameters(
                    parameterWithName("schoolYear")
                        .description("학년도"),
                    parameterWithName("schoolId")
                        .description("학교 아이디"),
                    parameterWithName("reportId")
                        .description("신고 아이디")
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
                    fieldWithPath("data.reportId").type(JsonFieldType.NUMBER)
                        .description("신고 아이디"),
                    fieldWithPath("data.schoolYear").type(JsonFieldType.NUMBER)
                        .description("학년도"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("신고 유형"),
                    fieldWithPath("data.status").type(JsonFieldType.STRING)
                        .description("신고 처리 상태"),
                    fieldWithPath("data.witness").type(JsonFieldType.STRING)
                        .description("신고자"),
                    fieldWithPath("data.who").type(JsonFieldType.STRING)
                        .description("누가"),
                    fieldWithPath("data.when").type(JsonFieldType.STRING)
                        .description("언제"),
                    fieldWithPath("data.where").type(JsonFieldType.STRING)
                        .description("어디서"),
                    fieldWithPath("data.what").type(JsonFieldType.STRING)
                        .description("무엇을"),
                    fieldWithPath("data.how").type(JsonFieldType.STRING)
                        .optional()
                        .description("어떻게"),
                    fieldWithPath("data.why").type(JsonFieldType.STRING)
                        .optional()
                        .description("왜"),
                    fieldWithPath("data.description").type(JsonFieldType.STRING)
                        .description("설명"),
                    fieldWithPath("data.result").type(JsonFieldType.STRING)
                        .optional()
                        .description("처리 결과"),
                    fieldWithPath("data.files").type(JsonFieldType.ARRAY)
                        .optional()
                        .description("첨부 파일 URL")
                )
            ));
    }
}
