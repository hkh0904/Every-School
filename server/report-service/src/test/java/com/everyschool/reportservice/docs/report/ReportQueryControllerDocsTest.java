package com.everyschool.reportservice.docs.report;

import com.everyschool.reportservice.api.controller.report.ReportQueryController;
import com.everyschool.reportservice.api.service.report.ReportQueryService;
import com.everyschool.reportservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
        mockMvc.perform(
            get("/report-service/v1/schools/{schoolId}/received-reports", 1L)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-received-report",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("typeId").type(JsonFieldType.NUMBER)
                        .description("신고 타입"),
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("신고 제목"),
                    fieldWithPath("description").type(JsonFieldType.STRING)
                        .description("신고 설명"),
                    fieldWithPath("who").type(JsonFieldType.STRING)
                        .description("누가"),
                    fieldWithPath("when").type(JsonFieldType.STRING)
                        .description("언제"),
                    fieldWithPath("where").type(JsonFieldType.STRING)
                        .description("어디서"),
                    fieldWithPath("what").type(JsonFieldType.STRING)
                        .description("무엇을"),
                    fieldWithPath("how").type(JsonFieldType.STRING)
                        .optional()
                        .description("어떻게"),
                    fieldWithPath("why").type(JsonFieldType.STRING)
                        .optional()
                        .description("왜"),
                    fieldWithPath("files").type(JsonFieldType.ARRAY)
                        .description("파일")
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
                        .description("응답 데이터"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("응답 데이터"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("응답 데이터")
                )
            ));
    }
}
