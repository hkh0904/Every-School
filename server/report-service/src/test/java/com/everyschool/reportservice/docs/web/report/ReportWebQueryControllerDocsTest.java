package com.everyschool.reportservice.docs.web.report;

import com.everyschool.reportservice.api.web.controller.report.ReportWebQueryController;
import com.everyschool.reportservice.api.web.controller.report.response.ReportDetailResponse;
import com.everyschool.reportservice.api.web.controller.report.response.ReportResponse;
import com.everyschool.reportservice.api.web.service.report.ReportWebQueryService;
import com.everyschool.reportservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static com.everyschool.reportservice.domain.report.ProgressStatus.*;
import static com.everyschool.reportservice.domain.report.ReportType.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
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

public class ReportWebQueryControllerDocsTest extends RestDocsSupport {

    private final ReportWebQueryService reportWebQueryService = mock(ReportWebQueryService.class);
    private final String BASE_URL = "/report-service/v1/web/{schoolYear}/schools/{schoolId}/reports";

    @Override
    protected Object initController() {
        return new ReportWebQueryController(reportWebQueryService);
    }

    @DisplayName("신고 목록 조회 API")
    @Test
    void searchReports() throws Exception {
        ReportResponse response1 = createReportResponse(1L, VIOLENCE.getCode(), REGISTER.getCode());
        ReportResponse response2 = createReportResponse(2L, ETC.getCode(), REGISTER.getCode());

        given(reportWebQueryService.searchReports(anyLong(), anyInt(), anyInt()))
            .willReturn(List.of(response1, response2));

        mockMvc.perform(
                get(BASE_URL, 2023, 21617)
                    .header("Authorization", "Bearer Access Token")
                    .param("status", "7001")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-reports",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("status")
                        .description("신고 처리 상태 코드")
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
                        .description("조회된 신고 목록수"),
                    fieldWithPath("data.content[].reportId").type(JsonFieldType.NUMBER)
                        .description("신고 id"),
                    fieldWithPath("data.content[].type").type(JsonFieldType.STRING)
                        .description("신고 유형"),
                    fieldWithPath("data.content[].status").type(JsonFieldType.STRING)
                        .description("신고 처리 상태"),
                    fieldWithPath("data.content[].date").type(JsonFieldType.ARRAY)
                        .description("신고 일시")
                )
            ));
    }

    @DisplayName("신고 상세 조회 API")
    @Test
    void searchReport() throws Exception {
        ReportDetailResponse response = ReportDetailResponse.builder()
            .reportId(1L)
            .schoolYear(2023)
            .typeId(VIOLENCE.getCode())
            .statusId(REGISTER.getCode())
            .witness("10301 문동은")
            .who("10302 박연진")
            .when("2023-11-04 11시경")
            .where("학교 체육관")
            .what("고데기로 화상을 입혔습니다.")
            .how("")
            .why("")
            .description("박연진이 고데기 열을 체크해달라며 제 팔에 댔습니다. 현재 화상을 입은 상태입니다.")
            .files(List.of("file_url1", "file_url2"))
            .build();

        given(reportWebQueryService.searchReport(anyLong()))
            .willReturn(response);

        mockMvc.perform(
                get(BASE_URL + "/{reportId}", 2023, 21617, 1)
                    .header("Authorization", "Bearer Access Token")
            ).andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-report",
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
                    fieldWithPath("data.reportId").type(JsonFieldType.NUMBER)
                        .description("신고 id"),
                    fieldWithPath("data.schoolYear").type(JsonFieldType.NUMBER)
                        .description("신고 학년도"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("신고 유형"),
                    fieldWithPath("data.witness").type(JsonFieldType.STRING)
                        .description("목격자"),
                    fieldWithPath("data.status").type(JsonFieldType.STRING)
                        .description("신고 처리 상태"),
                    fieldWithPath("data.who").type(JsonFieldType.STRING)
                        .description("누가"),
                    fieldWithPath("data.when").type(JsonFieldType.STRING)
                        .description("언제"),
                    fieldWithPath("data.where").type(JsonFieldType.STRING)
                        .description("어디서"),
                    fieldWithPath("data.what").type(JsonFieldType.STRING)
                        .description("무엇을"),
                    fieldWithPath("data.how").type(JsonFieldType.STRING)
                        .description("어떻게"),
                    fieldWithPath("data.why").type(JsonFieldType.STRING)
                        .description("왜"),
                    fieldWithPath("data.description").type(JsonFieldType.STRING)
                        .description("신고 설명"),
                    fieldWithPath("data.files").type(JsonFieldType.ARRAY)
                        .description("첨부 파일")
                )
            ));
    }

    private ReportResponse createReportResponse(Long reportId, int typeId, int statusId) {
        return ReportResponse.builder()
            .reportId(reportId)
            .typeId(typeId)
            .statusId(statusId)
            .date(LocalDateTime.now())
            .build();
    }
}
