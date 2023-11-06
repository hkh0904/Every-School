package com.everyschool.reportservice.docs.app.report;

import com.everyschool.reportservice.api.app.controller.report.ReportAppController;
import com.everyschool.reportservice.api.app.controller.report.request.CreateReportRequest;
import com.everyschool.reportservice.api.app.controller.report.response.CreateReportResponse;
import com.everyschool.reportservice.api.app.service.report.ReportAppService;
import com.everyschool.reportservice.api.FileStore;
import com.everyschool.reportservice.docs.RestDocsSupport;
import com.everyschool.reportservice.domain.report.ReportType;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportAppControllerDocsTest extends RestDocsSupport {

    private final ReportAppService reportAppService = mock(ReportAppService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private final FileStore fileStore = mock(FileStore.class);
    private static final String BASE_URL = "/report-service/v1/app/{schoolYear}/schools/{schoolId}/reports";

    @Override
    protected Object initController() {
        return new ReportAppController(reportAppService, tokenUtils, fileStore);
    }

    @DisplayName("신고 등록 API")
    @Test
    void createReport() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        given(fileStore.storeFiles(anyList()))
            .willReturn(new ArrayList<>());

        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(ReportType.VIOLENCE.getCode())
            .description("이예리가 빵 사오래요")
            .who("이예리")
            .when("2023-11-06 12시경")
            .where("C221")
            .what("빵셔틀")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        CreateReportResponse response = CreateReportResponse.builder()
            .reportId(1L)
            .title("20201 동팔이")
            .createdDate(LocalDateTime.now())
            .build();

        given(reportAppService.createReport(anyString(), anyLong(), anyInt(), any(), anyList()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .header("Authorization", "Bearer Access Token")
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
                    fieldWithPath("description").type(JsonFieldType.STRING)
                        .description("설명"),
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
                        .optional()
                        .description("첨부 파일")
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
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("신고자"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("신고 일시")
                )
            ));
    }
}
