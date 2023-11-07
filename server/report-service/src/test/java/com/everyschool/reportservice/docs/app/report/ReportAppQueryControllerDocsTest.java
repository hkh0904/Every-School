package com.everyschool.reportservice.docs.app.report;

import com.everyschool.reportservice.api.app.controller.report.ReportAppQueryController;
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
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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

        given(reportAppQueryService.searchReports(anyString(), anyInt()))
            .willReturn(List.of(response));

        mockMvc.perform(
            get(BASE_URL, 2023, 100000)
                .header("Authorization", "Bearer Access Token")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-reports",
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
}
