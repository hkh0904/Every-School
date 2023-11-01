package com.everyschool.reportservice.docs.report;

import com.everyschool.reportservice.api.controller.FileStore;
import com.everyschool.reportservice.api.controller.report.ReportController;
import com.everyschool.reportservice.api.controller.report.request.CreateReportRequest;
import com.everyschool.reportservice.api.controller.report.response.CreateReportResponse;
import com.everyschool.reportservice.api.service.report.ReportService;
import com.everyschool.reportservice.api.service.report.dto.CreateReportDto;
import com.everyschool.reportservice.docs.RestDocsSupport;
import com.everyschool.reportservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportControllerDocsTest extends RestDocsSupport {

    private final ReportService reportService = mock(ReportService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private final FileStore fileStore = mock(FileStore.class);

    @Override
    protected Object initController() {
        return new ReportController(reportService, tokenUtils, fileStore);
    }

    @DisplayName("신고 등록 API")
    @Test
    void createReport() throws Exception {
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(4001)
            .title("리온이가 너무 귀여워요!")
            .description("치명적이에요!")
            .who("이리온")
            .when("2023-10-31 10:30")
            .where("우리집")
            .what("애교쟁이에요.")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        given(fileStore.storeFiles(anyList()))
            .willReturn(new ArrayList<>());

        CreateReportResponse response = CreateReportResponse.builder()
            .reportId(1L)
            .title("리온이가 너무 귀여워요!")
            .createdDate(LocalDateTime.now())
            .build();

        given(reportService.createReport(anyString(), anyLong(), any(CreateReportDto.class), anyList()))
            .willReturn(response);

        mockMvc.perform(
            post("/report-service/v1/schools/{schoolId}/reports", 100L)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-report",
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
