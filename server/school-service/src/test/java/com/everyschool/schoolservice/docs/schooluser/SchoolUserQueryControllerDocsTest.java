package com.everyschool.schoolservice.docs.schooluser;

import com.everyschool.schoolservice.api.controller.schooluser.SchoolUserQueryController;
import com.everyschool.schoolservice.api.controller.schooluser.response.MyClassStudentResponse;
import com.everyschool.schoolservice.api.service.schooluser.SchoolUserQueryService;
import com.everyschool.schoolservice.docs.RestDocsSupport;
import com.everyschool.schoolservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SchoolUserQueryControllerDocsTest extends RestDocsSupport {

    private final SchoolUserQueryService schoolUserQueryService = mock(SchoolUserQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);

    @Override
    protected Object initController() {
        return new SchoolUserQueryController(schoolUserQueryService, tokenUtils);
    }

    @DisplayName("학급 학생 조회 API")
    @Test
    void searchMyClassStudents() throws Exception {

        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        MyClassStudentResponse response1 = MyClassStudentResponse.builder()
            .userId(1L)
            .studentNumber(10301)
            .name("이예리")
            .birth("1998.04.12")
            .build();

        MyClassStudentResponse response2 = MyClassStudentResponse.builder()
            .userId(2L)
            .studentNumber(10302)
            .name("이리온")
            .birth("1998.12.10")
            .build();

        given(schoolUserQueryService.searchMyClassStudents(anyString(), anyInt()))
            .willReturn(List.of(response1, response2));

        mockMvc.perform(
                get("/school-service/v1/schools/{schoolId}/classes/{schoolYear}", 1L, 2023)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-my-class-students",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터"),
                    fieldWithPath("data[].userId").type(JsonFieldType.NUMBER)
                        .description("학생 id"),
                    fieldWithPath("data[].studentNumber").type(JsonFieldType.NUMBER)
                        .description("학생 학번"),
                    fieldWithPath("data[].name").type(JsonFieldType.STRING)
                        .description("학생 이름"),
                    fieldWithPath("data[].birth").type(JsonFieldType.STRING)
                        .description("학생 생년월일")
                )
            ));
    }
}
