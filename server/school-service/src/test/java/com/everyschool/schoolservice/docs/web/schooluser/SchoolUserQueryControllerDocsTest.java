package com.everyschool.schoolservice.docs.web.schooluser;

import com.everyschool.schoolservice.api.web.controller.schooluser.SchoolUserWebQueryController;
import com.everyschool.schoolservice.api.web.controller.schooluser.response.MyClassParentResponse;
import com.everyschool.schoolservice.api.web.controller.schooluser.response.MyClassStudentResponse;
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

public class SchoolUserQueryControllerDocsTest extends RestDocsSupport {

    private final SchoolUserQueryService schoolUserQueryService = mock(SchoolUserQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/school-service/v1/web/{schoolYear}/schools/{schoolId}/classes/{schoolClassId}";

    @Override
    protected Object initController() {
        return new SchoolUserWebQueryController(schoolUserQueryService, tokenUtils);
    }

    @DisplayName("나의 학급 학생 조회 API")
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
                get(BASE_URL + "/students", 2023, 100000, 2)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("web-search-my-class-students",
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
                    parameterWithName("schoolClassId")
                        .description("학급 아이디")
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
                        .description("우리반 총원"),
                    fieldWithPath("data.content[].userId").type(JsonFieldType.NUMBER)
                        .description("학생 id"),
                    fieldWithPath("data.content[].studentNumber").type(JsonFieldType.NUMBER)
                        .description("학생 학번"),
                    fieldWithPath("data.content[].name").type(JsonFieldType.STRING)
                        .description("학생 이름"),
                    fieldWithPath("data.content[].birth").type(JsonFieldType.STRING)
                        .description("학생 생년월일")
                )
            ));
    }

    @DisplayName("나의 학급 학부모 조회 API")
    @Test
    void searchMyClassParents() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        MyClassParentResponse response1 = MyClassParentResponse.builder()
            .userId(1L)
            .studentNumber(10301)
            .studentName("하예솔")
            .parentType('F')
            .name("박연진")
            .build();

        MyClassParentResponse response2 = MyClassParentResponse.builder()
            .userId(2L)
            .studentNumber(10301)
            .studentName("하예솔")
            .parentType('M')
            .name("하도영")
            .build();

        given(schoolUserQueryService.searchMyClassParents(anyString(), anyInt()))
            .willReturn(List.of(response1, response2));

        mockMvc.perform(
                get(BASE_URL + "/parents", 2023, 100000, 2)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("web-search-my-class-parents",
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
                    parameterWithName("schoolClassId")
                        .description("학급 아이디")
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
                        .description("등록된 학부모 총원"),
                    fieldWithPath("data.content[].userId").type(JsonFieldType.NUMBER)
                        .description("학부모 id"),
                    fieldWithPath("data.content[].studentNumber").type(JsonFieldType.NUMBER)
                        .description("자녀 학번"),
                    fieldWithPath("data.content[].studentName").type(JsonFieldType.STRING)
                        .description("자녀 이름"),
                    fieldWithPath("data.content[].parentType").type(JsonFieldType.STRING)
                        .description("자녀와의 관계"),
                    fieldWithPath("data.content[].name").type(JsonFieldType.STRING)
                        .description("학부모 이름")
                )
            ));
    }
}
