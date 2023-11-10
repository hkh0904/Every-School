package com.everyschool.userservice.docs.app.user;

import com.everyschool.userservice.api.app.controller.user.UserAppQueryController;
import com.everyschool.userservice.api.app.controller.user.response.StudentContactInfoResponse;
import com.everyschool.userservice.api.app.controller.user.response.TeacherContactInfoResponse;
import com.everyschool.userservice.api.app.service.user.UserAppQueryService;
import com.everyschool.userservice.docs.RestDocsSupport;
import com.everyschool.userservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAppQueryControllerDocsTest extends RestDocsSupport {

    private final UserAppQueryService userAppQueryService = mock(UserAppQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/v1/app/{schoolYear}/schools/{schoolId}";

    @Override
    protected Object initController() {
        return new UserAppQueryController(userAppQueryService, tokenUtils);
    }

    @DisplayName("교직원 연락처 조회 API")
    @Test
    void searchUserContactInfo() throws Exception {
        String userKey = UUID.randomUUID().toString();

        given(tokenUtils.getUserKey())
            .willReturn(userKey);

        TeacherContactInfoResponse response = TeacherContactInfoResponse.builder()
            .userKey(userKey)
            .name("이예리")
            .build();

        given(userAppQueryService.searchContactInfo(anyString(), anyInt()))
            .willReturn(response);

        mockMvc.perform(
                get(BASE_URL + "/students", 2023, 100000)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("app-search-user-contact-info",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.userKey").type(JsonFieldType.STRING)
                        .description("회원 고유키"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("회원 이름"),
                    fieldWithPath("data.userType").type(JsonFieldType.STRING)
                        .description("회원 유형")
                )
            ));
    }

    @DisplayName("교직원 연락처 조회 API")
    @Test
    void searchUserContactInfos() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        StudentContactInfoResponse.Parent parent = StudentContactInfoResponse.Parent.builder()
            .parentKey(UUID.randomUUID().toString())
            .name("이예리")
            .parentType("F")
            .build();

        StudentContactInfoResponse response = StudentContactInfoResponse.builder()
            .userKey(UUID.randomUUID().toString())
            .name("이리온")
            .studentNumber(20201)
            .build();

        response.getParents().add(parent);

        given(userAppQueryService.searchContactInfos(anyInt(), anyString()))
            .willReturn(List.of(response));

        mockMvc.perform(
            get(BASE_URL + "/teachers", 2023, 100000)
                .header("Authorization", "Bearer Access Token")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("app-search-user-contact-infos",
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
                    fieldWithPath("data[].userKey").type(JsonFieldType.STRING)
                        .description("학생 고유키"),
                    fieldWithPath("data[].name").type(JsonFieldType.STRING)
                        .description("학생 이름"),
                    fieldWithPath("data[].studentNumber").type(JsonFieldType.NUMBER)
                        .description("학번"),
                    fieldWithPath("data[].parents[].parentKey").type(JsonFieldType.STRING)
                        .description("가족 고유키"),
                    fieldWithPath("data[].parents[].name").type(JsonFieldType.STRING)
                        .description("가족 이름"),
                    fieldWithPath("data[].parents[].parentType").type(JsonFieldType.STRING)
                        .description("가족 타입")
                )
            ));
    }
}
