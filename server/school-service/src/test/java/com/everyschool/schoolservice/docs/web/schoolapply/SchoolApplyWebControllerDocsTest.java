package com.everyschool.schoolservice.docs.web.schoolapply;

import com.everyschool.schoolservice.api.web.controller.schoolapply.SchoolApplyWebController;
import com.everyschool.schoolservice.api.web.controller.schoolapply.request.RejectSchoolApplyRequest;
import com.everyschool.schoolservice.api.web.controller.schoolapply.response.EditSchoolApplyResponse;
import com.everyschool.schoolservice.api.web.service.schoolapply.SchoolApplyWebService;
import com.everyschool.schoolservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SchoolApplyWebControllerDocsTest extends RestDocsSupport {

    private final SchoolApplyWebService schoolApplyWebService = mock(SchoolApplyWebService.class);
    private static final String BASE_URL = "/school-service/v1/web/{schoolYear}/schools/{schoolId}/apply";

    @Override
    protected Object initController() {
        return new SchoolApplyWebController(schoolApplyWebService);
    }

    @DisplayName("등록 신청 승인 API")
    @Test
    void approveSchoolApply() throws Exception {
        EditSchoolApplyResponse response = EditSchoolApplyResponse.builder()
            .schoolApplyId(1L)
            .schoolYear(2023)
            .isApproved(true)
            .rejectedReason(null)
            .lastModifiedDate(LocalDateTime.now())
            .build();

        given(schoolApplyWebService.approveSchoolApply(anyLong()))
            .willReturn(response);

        mockMvc.perform(
                patch(BASE_URL + "/{schoolApplyId}/approve", 2023, 100000, 1)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("web-approve-school-apply",
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
                        .description("신청 id"),
                    fieldWithPath("data.schoolYear").type(JsonFieldType.NUMBER)
                        .description("학년도"),
                    fieldWithPath("data.isApproved").type(JsonFieldType.BOOLEAN)
                        .description("승인 여부"),
                    fieldWithPath("data.rejectedReason").type(JsonFieldType.NULL)
                        .description("거절 사유"),
                    fieldWithPath("data.lastModifiedDate").type(JsonFieldType.ARRAY)
                        .description("승인 일시")
                )
            ));
    }

    @DisplayName("등록 신청 거절 API")
    @Test
    void rejectSchoolApply() throws Exception {
        RejectSchoolApplyRequest request = RejectSchoolApplyRequest.builder()
            .rejectedReason("학급 인원이 아닙니다.")
            .build();

        EditSchoolApplyResponse response = EditSchoolApplyResponse.builder()
            .schoolApplyId(1L)
            .schoolYear(2023)
            .isApproved(false)
            .rejectedReason("학급 인원이 아닙니다.")
            .lastModifiedDate(LocalDateTime.now())
            .build();

        given(schoolApplyWebService.rejectSchoolApply(anyLong(), anyString()))
            .willReturn(response);

        mockMvc.perform(
                patch(BASE_URL + "/{schoolApplyId}/reject", 2023, 100000, 1)
                    .header("Authorization", "Bearer Access Token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("web-reject-school-apply",
                preprocessRequest(prettyPrint()),
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
                requestFields(
                    fieldWithPath("rejectedReason").type(JsonFieldType.STRING)
                        .description("신청 거절 사유")
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
                        .description("신청 id"),
                    fieldWithPath("data.schoolYear").type(JsonFieldType.NUMBER)
                        .description("학년도"),
                    fieldWithPath("data.isApproved").type(JsonFieldType.BOOLEAN)
                        .description("승인 여부"),
                    fieldWithPath("data.rejectedReason").type(JsonFieldType.STRING)
                        .description("거절 사유"),
                    fieldWithPath("data.lastModifiedDate").type(JsonFieldType.ARRAY)
                        .description("승인 일시")
                )
            ));
    }
}
