package com.everyschool.reportservice.docs.report;

import com.everyschool.reportservice.api.controller.report.ReportQueryController;
import com.everyschool.reportservice.api.controller.report.response.ReportDetailResponse;
import com.everyschool.reportservice.api.controller.report.response.ReportResponse;
import com.everyschool.reportservice.api.service.report.ReportQueryService;
import com.everyschool.reportservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
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

public class ReportQueryControllerDocsTest extends RestDocsSupport {

    private final ReportQueryService reportQueryService = mock(ReportQueryService.class);

    @Override
    protected Object initController() {
        return new ReportQueryController(reportQueryService);
    }

    @DisplayName("접수된 신고 목록 조회 API")
    @Test
    void searchReceivedReport() throws Exception {

        ReportResponse.ReportVo vo = ReportResponse.ReportVo.builder()
            .reportId(1L)
            .typeId(5001)
            .date(LocalDateTime.now())
            .build();
        vo.setNo(1);

        ReportResponse response = ReportResponse.builder()
            .count(1)
            .reports(List.of(vo))
            .build();

        given(reportQueryService.searchReceivedReports(anyLong()))
            .willReturn(response);

        mockMvc.perform(
            get("/report-service/v1/schools/{schoolId}/received-reports", 1L)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-received-report",
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
                    fieldWithPath("data.count").type(JsonFieldType.NUMBER)
                        .description("대기 중 신고수"),
                    fieldWithPath("data.reports").type(JsonFieldType.ARRAY)
                        .description("신고 목록"),
                    fieldWithPath("data.reports[].no").type(JsonFieldType.NUMBER)
                        .description("신고 번호"),
                    fieldWithPath("data.reports[].reportId").type(JsonFieldType.NUMBER)
                        .description("신고 id"),
                    fieldWithPath("data.reports[].type").type(JsonFieldType.STRING)
                        .description("신고 타입"),
                    fieldWithPath("data.reports[].date").type(JsonFieldType.ARRAY)
                        .description("접수 일시")
                )
            ));
    }

    @DisplayName("처리 중인 신고 목록 조회 API")
    @Test
    void searchProcessedReports() throws Exception {

        ReportResponse.ReportVo vo = ReportResponse.ReportVo.builder()
            .reportId(1L)
            .typeId(5001)
            .date(LocalDateTime.now())
            .build();
        vo.setNo(1);

        ReportResponse response = ReportResponse.builder()
            .count(1)
            .reports(List.of(vo))
            .build();

        given(reportQueryService.searchProcessedReports(anyLong()))
            .willReturn(response);

        mockMvc.perform(
                get("/report-service/v1/schools/{schoolId}/processed-reports", 1L)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-processed-report",
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
                    fieldWithPath("data.count").type(JsonFieldType.NUMBER)
                        .description("처리 중 신고수"),
                    fieldWithPath("data.reports").type(JsonFieldType.ARRAY)
                        .description("신고 목록"),
                    fieldWithPath("data.reports[].no").type(JsonFieldType.NUMBER)
                        .description("신고 번호"),
                    fieldWithPath("data.reports[].reportId").type(JsonFieldType.NUMBER)
                        .description("신고 id"),
                    fieldWithPath("data.reports[].type").type(JsonFieldType.STRING)
                        .description("신고 타입"),
                    fieldWithPath("data.reports[].date").type(JsonFieldType.ARRAY)
                        .description("접수 일시")
                )
            ));
    }

    @DisplayName("신고 내역 상세 조회 API")
    @Test
    void searchReport() throws Exception {
        ReportDetailResponse response = ReportDetailResponse.builder()
            .reportId(1L)
            .reportUser("1학년 3반 1번 이예리")
            .typeId(5000)
            .progressStatusId(4003)
            .title("리온이가 괴롭혀요.")
            .description("리온이가 맨날 애교 부리면서 심장을 아프게 해요ㅜㅜ")
            .who("이리온")
            .when("2023.11.01 10시 30분경")
            .where("우리집")
            .what("애교 부려요")
            .how("")
            .why("")
            .result("처리 할 수 없는 신고 내용입니다.")
            .reportDate(LocalDateTime.now())
            .filePaths(List.of("url1", "url1"))
            .build();

        given(reportQueryService.searchReport(anyLong()))
            .willReturn(response);

        mockMvc.perform(
            get("/report-service/v1/schools/{schoolId}/reports/{reportId}", 1L, 2L)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-detail-report",
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
                    fieldWithPath("data.reportUser").type(JsonFieldType.STRING)
                        .description("신고자"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("신고 유형"),
                    fieldWithPath("data.progressStatus").type(JsonFieldType.STRING)
                        .description("진행 상태"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("신고 제목"),
                    fieldWithPath("data.description").type(JsonFieldType.STRING)
                        .description("신고 설명"),
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
                    fieldWithPath("data.result").type(JsonFieldType.STRING)
                        .description("처리 결과"),
                    fieldWithPath("data.reportDate").type(JsonFieldType.ARRAY)
                        .description("신고 일시"),
                    fieldWithPath("data.filePaths").type(JsonFieldType.ARRAY)
                        .description("첨부 파일 경로")
                )
            ));
    }
}
