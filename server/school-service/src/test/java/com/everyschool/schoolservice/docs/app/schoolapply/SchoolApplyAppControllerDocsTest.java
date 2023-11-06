package com.everyschool.schoolservice.docs.app.schoolapply;

import com.everyschool.schoolservice.api.app.controller.schoolapply.SchoolApplyAppController;
import com.everyschool.schoolservice.api.app.controller.schoolapply.response.CreateSchoolApplyResponse;
import com.everyschool.schoolservice.api.app.service.schoolapply.SchoolApplyAppService;
import com.everyschool.schoolservice.api.controller.schoolapply.request.CreateSchoolApplyRequest;
import com.everyschool.schoolservice.docs.RestDocsSupport;
import com.everyschool.schoolservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SchoolApplyAppControllerDocsTest extends RestDocsSupport {

    private final SchoolApplyAppService schoolApplyAppService = mock(SchoolApplyAppService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/school-service/v1/app/{schoolYear}/schools/{schoolId}/apply";

    @Override
    protected Object initController() {
        return new SchoolApplyAppController(schoolApplyAppService, tokenUtils);
    }

    @DisplayName("[학생] 학급 등록 신청 API")
    @Test
    void createSchoolApply() throws Exception {
        CreateSchoolApplyRequest request = CreateSchoolApplyRequest.builder()
            .grade(1)
            .classNum(3)
            .build();

        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        CreateSchoolApplyResponse response = CreateSchoolApplyResponse.builder()
            .schoolYear(2023)
            .grade(1)
            .classNum(3)
            .appliedDate(LocalDateTime.now())
            .build();

        given(schoolApplyAppService.createSchoolApply(anyString(), anyLong(), anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .header("Authorization", "Bearer Access Token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-school-apply",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("grade").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("신청 학년"),
                    fieldWithPath("classNum").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("신청 반")
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
                    fieldWithPath("data.schoolYear").type(JsonFieldType.NUMBER)
                        .description("신청 학년도"),
                    fieldWithPath("data.grade").type(JsonFieldType.NUMBER)
                        .description("신청 학년"),
                    fieldWithPath("data.classNum").type(JsonFieldType.NUMBER)
                        .description("신청 반"),
                    fieldWithPath("data.appliedDate").type(JsonFieldType.ARRAY)
                        .description("신청 일시")
                )
            ));
    }


}
