package com.everyschool.reportservice.docs.report;

import com.everyschool.reportservice.api.controller.report.ReportQueryController;
import com.everyschool.reportservice.api.controller.report.response.ReceivedReportResponse;
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

        ReceivedReportResponse.ReportVo vo = ReceivedReportResponse.ReportVo.builder()
            .reportId(1L)
            .typeId(5001)
            .date(LocalDateTime.now())
            .build();
        vo.setNo(1);

        ReceivedReportResponse response = ReceivedReportResponse.builder()
            .count(1)
            .reports(List.of(vo))
            .build();

        given(reportQueryService.searchReceivedReport(anyLong()))
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
                        .description("신고 등록 일시")
                )
            ));
    }
}
