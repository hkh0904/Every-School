package com.everyschool.schoolservice.docs.web.schoolapply;

import com.everyschool.schoolservice.api.web.controller.schoolapply.SchoolApplyWebQueryController;
import com.everyschool.schoolservice.api.web.controller.schoolapply.response.SchoolApplyResponse;
import com.everyschool.schoolservice.api.web.service.schoolapply.SchoolApplyWebQueryService;
import com.everyschool.schoolservice.docs.RestDocsSupport;
import com.everyschool.schoolservice.domain.schoolapply.ApplyType;
import com.everyschool.schoolservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SchoolApplyWebQueryControllerDocsTest extends RestDocsSupport {

    private final SchoolApplyWebQueryService schoolApplyWebQueryService = mock(SchoolApplyWebQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/school-service/v1/web/{schoolYear}/schools/{schoolId}";

    @Override
    protected Object initController() {
        return new SchoolApplyWebQueryController(schoolApplyWebQueryService, tokenUtils);
    }

    @DisplayName("학급 승인 대기 목록 조회 API")
    @Test
    void searchWaitSchoolApply() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        SchoolApplyResponse response1 = SchoolApplyResponse.builder()
            .schoolApplyId(1L)
            .applyType(ApplyType.STUDENT.getText())
            .studentInfo("10301 이예리")
            .lastModifiedDate(LocalDateTime.now())
            .build();

        SchoolApplyResponse response2 = SchoolApplyResponse.builder()
            .schoolApplyId(1L)
            .applyType(ApplyType.FATHER.getText())
            .studentInfo("10301 이예리")
            .lastModifiedDate(LocalDateTime.now())
            .build();

        given(schoolApplyWebQueryService.searchWaitSchoolApply(anyString(), anyInt()))
            .willReturn(List.of(response1, response2));

        mockMvc.perform(
            get(BASE_URL + "/wait-apply", 2023, 100000)
                .header("Authorization", "Bearer Access Token")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("web-search-wait-school-apply",
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
                    fieldWithPath("data.count").type(JsonFieldType.NUMBER)
                        .description("승인 대기 중인 신청 수"),
                    fieldWithPath("data.content[].schoolApplyId").type(JsonFieldType.NUMBER)
                        .description("승인 신청 id"),
                    fieldWithPath("data.content[].applyType").type(JsonFieldType.STRING)
                        .description("승인 신청 유형"),
                    fieldWithPath("data.content[].studentInfo").type(JsonFieldType.STRING)
                        .description("승인 신청 학생 정보"),
                    fieldWithPath("data.content[].lastModifiedDate").type(JsonFieldType.ARRAY)
                        .description("승인 신청 일시")
                )
            ));
    }

    @DisplayName("학급 승인 목록 조회 API")
    @Test
    void searchApproveSchoolApply() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        SchoolApplyResponse response1 = SchoolApplyResponse.builder()
            .schoolApplyId(1L)
            .applyType(ApplyType.STUDENT.getText())
            .studentInfo("10301 이예리")
            .lastModifiedDate(LocalDateTime.now())
            .build();

        SchoolApplyResponse response2 = SchoolApplyResponse.builder()
            .schoolApplyId(1L)
            .applyType(ApplyType.FATHER.getText())
            .studentInfo("10301 이예리")
            .lastModifiedDate(LocalDateTime.now())
            .build();

        given(schoolApplyWebQueryService.searchApproveSchoolApply(anyString(), anyInt()))
            .willReturn(List.of(response1, response2));

        mockMvc.perform(
                get(BASE_URL + "/approve-apply", 2023, 100000)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("web-search-approve-school-apply",
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
                    fieldWithPath("data.count").type(JsonFieldType.NUMBER)
                        .description("승인 대기 중인 신청 수"),
                    fieldWithPath("data.content[].schoolApplyId").type(JsonFieldType.NUMBER)
                        .description("승인 신청 id"),
                    fieldWithPath("data.content[].applyType").type(JsonFieldType.STRING)
                        .description("승인 신청 유형"),
                    fieldWithPath("data.content[].studentInfo").type(JsonFieldType.STRING)
                        .description("승인 신청 학생 정보"),
                    fieldWithPath("data.content[].lastModifiedDate").type(JsonFieldType.ARRAY)
                        .description("승인 일시")
                )
            ));
    }

    @DisplayName("학급 승인 상세 조회 API")
    @Test
    void searchSchoolApply() throws Exception {
        SchoolApplyResponse response = SchoolApplyResponse.builder()
            .schoolApplyId(1L)
            .applyType(ApplyType.STUDENT.getText())
            .studentInfo("10301 이예리")
            .lastModifiedDate(LocalDateTime.now())
            .build();

        given(schoolApplyWebQueryService.searchSchoolApply(anyLong()))
            .willReturn(response);

        mockMvc.perform(
                get(BASE_URL + "/apply/{schoolApplyId}", 2023, 100000, 1)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("web-search-detail-school-apply",
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
                    parameterWithName("schoolApplyId")
                        .description("신청 아이디")
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
                    fieldWithPath("data.schoolApplyId").type(JsonFieldType.NUMBER)
                        .description("승인 신청 id"),
                    fieldWithPath("data.applyType").type(JsonFieldType.STRING)
                        .description("승인 신청 유형"),
                    fieldWithPath("data.studentInfo").type(JsonFieldType.STRING)
                        .description("승인 신청 학생 정보"),
                    fieldWithPath("data.lastModifiedDate").type(JsonFieldType.ARRAY)
                        .description("승인 일시")
                )
            ));
    }
}
