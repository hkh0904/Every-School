package com.everyschool.schoolservice.docs.schoolapply;

import com.everyschool.schoolservice.api.controller.schoolapply.SchoolApplyQueryController;
import com.everyschool.schoolservice.api.controller.schoolapply.response.SchoolApplyResponse;
import com.everyschool.schoolservice.api.service.schoolapply.SchoolApplyQueryService;
import com.everyschool.schoolservice.docs.RestDocsSupport;
import com.everyschool.schoolservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
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

public class SchoolApplyQueryControllerDocsTest extends RestDocsSupport {

    private final SchoolApplyQueryService schoolApplyQueryService = mock(SchoolApplyQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);

    @Override
    protected Object initController() {
        return new SchoolApplyQueryController(schoolApplyQueryService, tokenUtils);
    }

    @DisplayName("학급 승인 목록 조회 API")
    @Test
    void searchSchoolApplies() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        SchoolApplyResponse response1 = SchoolApplyResponse.builder()
            .schoolApplyId(1L)
            .parentId(null)
            .childInfo("1학년 3반 이리온")
            .appliedDate(LocalDateTime.now())
            .build();

        SchoolApplyResponse response2 = SchoolApplyResponse.builder()
            .schoolApplyId(2L)
            .parentId(100L)
            .childInfo("1학년 3반 이리온")
            .appliedDate(LocalDateTime.now())
            .build();

        given(schoolApplyQueryService.searchSchoolApplies(anyString(), anyString()))
            .willReturn(List.of(response1, response2));

        mockMvc.perform(
                get("/school-service/v1/schools/{schoolId}/apply", 1L)
                    .header("Authorization", "Bearer Access Token")
                    .param("status", "wait")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-school-applies",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("status")
                        .description("조회할 상태")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터"),
                    fieldWithPath("data[].schoolApplyId").type(JsonFieldType.NUMBER)
                        .description("학급 승인 id"),
                    fieldWithPath("data[].type").type(JsonFieldType.STRING)
                        .description("신청 타입"),
                    fieldWithPath("data[].childInfo").type(JsonFieldType.STRING)
                        .description("학생(자식) 정보"),
                    fieldWithPath("data[].relationship").type(JsonFieldType.STRING)
                        .description("학생(자식)과의 관계"),
                    fieldWithPath("data[].appliedDate").type(JsonFieldType.ARRAY)
                        .description("신청 일시")
                )
            ));
    }

    @DisplayName("학급 승인 상세 조회 API")
    @Test
    void searchSchoolApply() {
        //given

        //when

        //then

    }
}
