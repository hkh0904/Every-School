package com.everyschool.schoolservice.docs.schoolapply;

import com.everyschool.schoolservice.api.controller.schoolapply.SchoolApplyController;
import com.everyschool.schoolservice.api.controller.schoolapply.request.CreateSchoolApplyRequest;
import com.everyschool.schoolservice.api.controller.schoolapply.response.CreateSchoolApplyResponse;
import com.everyschool.schoolservice.api.service.schoolapply.SchoolApplyService;
import com.everyschool.schoolservice.api.service.schoolapply.dto.CreateSchoolApplyDto;
import com.everyschool.schoolservice.docs.RestDocsSupport;
import com.everyschool.schoolservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
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

public class SchoolApplyControllerDocsTest extends RestDocsSupport {

    private final SchoolApplyService schoolApplyService = mock(SchoolApplyService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);

    @Override
    protected Object initController() {
        return new SchoolApplyController(schoolApplyService, tokenUtils);
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

        given(schoolApplyService.createSchoolApply(anyLong(), anyString(), any(CreateSchoolApplyDto.class)))
            .willReturn(response);

        mockMvc.perform(
                post("/school-service/v1/schools/{schoolId}/apply", 1L)
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
