package com.everyschool.reportservice.docs.web.report;

import com.everyschool.reportservice.api.web.controller.report.ReportWebQueryController;
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
    private final String URL = "/report-service/v1/web/{schoolYear}/schools/{schoolId}/reports";

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
                get(URL, 2023, 21617)
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

    private ReportResponse createReportResponse(Long reportId, int typeId, int statusId) {
        return ReportResponse.builder()
            .reportId(reportId)
            .typeId(typeId)
            .statusId(statusId)
            .date(LocalDateTime.now())
            .build();
    }
}
