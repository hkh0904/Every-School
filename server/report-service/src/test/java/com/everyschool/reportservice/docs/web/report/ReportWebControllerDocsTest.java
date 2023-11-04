package com.everyschool.reportservice.docs.web.report;

import com.everyschool.reportservice.api.web.controller.report.ReportWebController;
import com.everyschool.reportservice.api.web.controller.report.response.EditReportResponse;
import com.everyschool.reportservice.api.web.service.report.ReportWebService;
import com.everyschool.reportservice.docs.RestDocsSupport;
import com.everyschool.reportservice.domain.report.ProgressStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static com.everyschool.reportservice.domain.report.ProgressStatus.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportWebControllerDocsTest extends RestDocsSupport {

    private final ReportWebService reportWebService = mock(ReportWebService.class);
    private final String URL = "/report-service/v1/web/{schoolYear}/schools/{schoolId}/reports";

    @Override
    protected Object initController() {
        return new ReportWebController(reportWebService);
    }

    @DisplayName("신고 처리 상태 변경 API")
    @Test
    void editStatus() throws Exception {
        EditReportResponse response = EditReportResponse.builder()
            .reportId(1L)
            .statusId(PROCESS.getCode())
            .lastModifiedDate(LocalDateTime.now())
            .build();

        given(reportWebService.editStatus(anyLong(), anyInt()))
            .willReturn(response);

        mockMvc.perform(
            patch(URL + "/{reportId}", 2023, 21617, 1)
                .header("Authorization", "Bearer Access Token")
                .param("status", "7002")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("edit-status",
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
                    fieldWithPath("data.reportId").type(JsonFieldType.NUMBER)
                        .description("신고 id"),
                    fieldWithPath("data.status").type(JsonFieldType.STRING)
                        .description("신고 처리 상태"),
                    fieldWithPath("data.lastModifiedDate").type(JsonFieldType.ARRAY)
                        .description("수정 일시")
                )
            ));
    }
}
