package com.everyschool.userservice.docs.web.user;

import com.everyschool.userservice.api.web.controller.user.UserWebQueryController;
import com.everyschool.userservice.api.web.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.api.web.service.user.UserWebQueryService;
import com.everyschool.userservice.docs.RestDocsSupport;
import com.everyschool.userservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.everyschool.userservice.domain.user.UserType.TEACHER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserWebQueryControllerDocsTest extends RestDocsSupport {

    private final UserWebQueryService userWebQueryService = mock(UserWebQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/v1/web";

    @Override
    protected Object initController() {
        return new UserWebQueryController(userWebQueryService, tokenUtils);
    }

    @DisplayName("회원 정보 조회 API")
    @Test
    void searchUserInfo() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        UserInfoResponse.School school = UserInfoResponse.School.builder()
            .schoolId(21617L)
            .name("고실중학교")
            .build();

        UserInfoResponse.SchoolClass schoolClass = UserInfoResponse.SchoolClass.builder()
            .schoolClassId(1L)
            .grade(1)
            .classNum(3)
            .build();

        UserInfoResponse response = UserInfoResponse.builder()
            .userType(TEACHER.getCode())
            .email("ssafy@gmail.com")
            .name("이예리")
            .birth("1998-04-12")
            .school(school)
            .schoolClass(schoolClass)
            .joinDate(LocalDateTime.now())
            .build();

        given(userWebQueryService.searchUserInfo(anyString()))
            .willReturn(response);

        mockMvc.perform(
                get(BASE_URL + "/info")
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("web-search-user-info",
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
                    fieldWithPath("data.userType").type(JsonFieldType.NUMBER)
                        .description("회원 유형 코드"),
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("회원 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("회원 이름"),
                    fieldWithPath("data.birth").type(JsonFieldType.STRING)
                        .description("회원 생년원일"),
                    fieldWithPath("data.school.schoolId").type(JsonFieldType.NUMBER)
                        .description("학교 id"),
                    fieldWithPath("data.school.name").type(JsonFieldType.STRING)
                        .description("학교 이름"),
                    fieldWithPath("data.schoolClass.schoolClassId").type(JsonFieldType.NUMBER)
                        .description("학급 id"),
                    fieldWithPath("data.schoolClass.grade").type(JsonFieldType.NUMBER)
                        .description("학급 학년"),
                    fieldWithPath("data.schoolClass.classNum").type(JsonFieldType.NUMBER)
                        .description("학급 반"),
                    fieldWithPath("data.joinDate").type(JsonFieldType.ARRAY)
                        .description("가입 일시")
                )
            ));
    }
}
