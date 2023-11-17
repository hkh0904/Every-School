package com.everyschool.reportservice.docs.app.report;

import com.everyschool.reportservice.api.app.controller.report.ReportAppController;
import com.everyschool.reportservice.api.app.controller.report.request.CreateReportRequest;
import com.everyschool.reportservice.api.app.controller.report.response.CreateReportResponse;
import com.everyschool.reportservice.api.app.controller.report.response.RemoveReportResponse;
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

import static com.everyschool.reportservice.domain.report.ReportType.VIOLENCE;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
            .typeId(VIOLENCE.getCode())
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
            .witness("2학년 2반 1번 동팔이")
            .createdDate(LocalDateTime.now())
            .build();

        given(reportAppService.createReport(anyString(), anyInt(), anyLong(), any(), anyList()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .header("Authorization", "Bearer Access Token")
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("description", request.getDescription())
                    .param("who", request.getWho())
                    .param("when", request.getWhen())
                    .param("where", request.getWhere())
                    .param("what", request.getWhat())
                    .param("how", request.getHow())
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-report",
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
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.reportId").type(JsonFieldType.NUMBER)
                        .description("신고 id"),
                    fieldWithPath("data.witness").type(JsonFieldType.STRING)
                        .description("신고자"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("신고 일시")
                )
            ));
    }

    @DisplayName("신고 내역 삭제 API")
    @Test
    void removeReport() throws Exception {
        RemoveReportResponse response = RemoveReportResponse.builder()
            .reportId(1L)
            .type(VIOLENCE.getText())
            .schoolYear(2023)
            .witness("1학년 3반 13번 이예리 학생")
            .removedDate(LocalDateTime.now())
            .build();

        given(reportAppService.removeReport(anyLong()))
            .willReturn(response);

        mockMvc.perform(
                delete(BASE_URL + "/{reportId}", 2023, 100000, 1)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-report",
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
                        .description("신고 id"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("신고 유형"),
                    fieldWithPath("data.schoolYear").type(JsonFieldType.NUMBER)
                        .description("신고 학년도"),
                    fieldWithPath("data.witness").type(JsonFieldType.STRING)
                        .description("신고자"),
                    fieldWithPath("data.removedDate").type(JsonFieldType.ARRAY)
                        .description("신고 일시")
                )
            ));
    }
}
