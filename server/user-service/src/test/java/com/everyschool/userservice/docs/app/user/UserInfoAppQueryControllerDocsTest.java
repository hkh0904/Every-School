package com.everyschool.userservice.docs.app.user;

import com.everyschool.userservice.api.app.controller.user.UserInfoAppQueryController;
import com.everyschool.userservice.api.app.controller.user.response.ParentInfoResponse;
import com.everyschool.userservice.api.app.controller.user.response.StudentInfoResponse;
import com.everyschool.userservice.api.app.controller.user.response.TeacherInfoResponse;
import com.everyschool.userservice.api.app.controller.user.response.info.School;
import com.everyschool.userservice.api.app.controller.user.response.info.SchoolClass;
import com.everyschool.userservice.api.app.service.user.UserAppQueryService;
import com.everyschool.userservice.api.app.service.user.dto.ParentInfoResponseDto;
import com.everyschool.userservice.docs.RestDocsSupport;
import com.everyschool.userservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.everyschool.userservice.domain.user.UserType.*;
import static org.mockito.BDDMockito.anyString;
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

public class UserInfoAppQueryControllerDocsTest extends RestDocsSupport {

    private final UserAppQueryService userAppQueryService = mock(UserAppQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/v1/app";

    @Override
    protected Object initController() {
        return new UserInfoAppQueryController(userAppQueryService, tokenUtils);
    }

    @DisplayName("학생 정보 조회 API")
    @Test
    void searchStudentInfo() throws Exception {
        given(tokenUtils.getUserKey())
                .willReturn(UUID.randomUUID().toString());

        School school = School.builder()
                .schoolId(21617L)
                .name("고실중학교")
                .build();

        SchoolClass schoolClass = SchoolClass.builder()
                .schoolClassId(1L)
                .schoolYear(2023)
                .grade(1)
                .classNum(3)
                .build();

        StudentInfoResponse response = StudentInfoResponse.builder()
                .userType(STUDENT.getCode())
                .email("student@gmail.com")
                .name("이예리")
                .birth("1998-04-12")
                .school(school)
                .schoolClass(schoolClass)
                .joinDate(LocalDateTime.now())
                .build();

        given(userAppQueryService.searchStudentInfo(anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get(BASE_URL + "/info/student")
                                .header("Authorization", "Bearer Access Token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("app-search-student-info",
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
                                fieldWithPath("data.schoolClass.schoolYear").type(JsonFieldType.NUMBER)
                                        .description("학년도"),
                                fieldWithPath("data.schoolClass.grade").type(JsonFieldType.NUMBER)
                                        .description("학급 학년"),
                                fieldWithPath("data.schoolClass.classNum").type(JsonFieldType.NUMBER)
                                        .description("학급 반"),
                                fieldWithPath("data.joinDate").type(JsonFieldType.ARRAY)
                                        .description("가입 일시")
                        )
                ));
    }

    @DisplayName("학부모 정보 조회 API")
    @Test
    void searchParentInfo() throws Exception {
        given(tokenUtils.getUserKey())
                .willReturn(UUID.randomUUID().toString());
        School school = School.builder()
                .schoolId(21617L)
                .name("고실중학교")
                .build();

        SchoolClass schoolClass = SchoolClass.builder()
                .schoolClassId(1L)
                .schoolYear(2023)
                .grade(1)
                .classNum(3)
                .build();
        ParentInfoResponse.Descendant descendant = ParentInfoResponse.Descendant.builder()
                .userId(2L)
                .userKey(UUID.randomUUID().toString())
                .userType(STUDENT.getCode())
                .studentNumber(10301)
                .name("이리온")
                .school(school)
                .schoolClass(schoolClass)
                .build();
        ParentInfoResponse response = ParentInfoResponse.builder()
                .userType(PARENT.getCode())
                .email("parent@gmail.com")
                .name("이예리")
                .birth("1998-04-12")
                .descendants(List.of(descendant))
                .joinDate(LocalDateTime.now())
                .build();

        ParentInfoResponseDto dto = ParentInfoResponseDto.builder()
                .parentInfoResponse(response)
                .status(1)
                .build();

        given(userAppQueryService.searchParentInfo(anyString()))
                .willReturn(dto);

        mockMvc.perform(
                        get(BASE_URL + "/info/parent")
                                .header("Authorization", "Bearer Access Token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("app-search-parent-info",
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
                                fieldWithPath("data.descendants").type(JsonFieldType.ARRAY)
                                        .description("자식 정보"),
                                fieldWithPath("data.joinDate").type(JsonFieldType.ARRAY)
                                        .description("가입 일시"),
                                fieldWithPath("data.descendants[].userId").type(JsonFieldType.NUMBER)
                                        .description("자식 회원 아이디"),
                                fieldWithPath("data.descendants[].userKey").type(JsonFieldType.STRING)
                                        .description("자식 회원 고유키"),
                                fieldWithPath("data.descendants[].userType").type(JsonFieldType.NUMBER)
                                        .description("자식 회원 유형 코드"),
                                fieldWithPath("data.descendants[].name").type(JsonFieldType.STRING)
                                        .description("자식 회원 이름"),
                                fieldWithPath("data.descendants[].studentNumber").type(JsonFieldType.NUMBER)
                                        .description("자식 회원 학번"),
                                fieldWithPath("data.descendants[].school").type(JsonFieldType.OBJECT)
                                        .description("자식 회원 학교 정보"),
                                fieldWithPath("data.descendants[].school.schoolId").type(JsonFieldType.NUMBER)
                                        .description("자식 회원 학교 id"),
                                fieldWithPath("data.descendants[].school.name").type(JsonFieldType.STRING)
                                        .description("자식 회원 학교 이름"),
                                fieldWithPath("data.descendants[].schoolClass").type(JsonFieldType.OBJECT)
                                        .description("자식 회원 학급 정보"),
                                fieldWithPath("data.descendants[].schoolClass.schoolClassId").type(JsonFieldType.NUMBER)
                                        .description("자식 회원 학급 id"),
                                fieldWithPath("data.descendants[].schoolClass.schoolYear").type(JsonFieldType.NUMBER)
                                        .description("자식 회원 학급 학년도"),
                                fieldWithPath("data.descendants[].schoolClass.grade").type(JsonFieldType.NUMBER)
                                        .description("자식 회원 학급 학년"),
                                fieldWithPath("data.descendants[].schoolClass.classNum").type(JsonFieldType.NUMBER)
                                        .description("자식 회원 학급 반")
                        )
                ));
    }

    @DisplayName("교직원 정보 조회 API")
    @Test
    void searchTeacherInfo() throws Exception {
        given(tokenUtils.getUserKey())
                .willReturn(UUID.randomUUID().toString());
        School school = School.builder()
                .schoolId(21617L)
                .name("고실중학교")
                .build();

        SchoolClass schoolClass = SchoolClass.builder()
                .schoolClassId(1L)
                .schoolYear(2023)
                .grade(1)
                .classNum(3)
                .build();

        TeacherInfoResponse response = TeacherInfoResponse.builder()
                .userType(TEACHER.getCode())
                .email("teacher@gmail.com")
                .name("이예리")
                .birth("1998-04-12")
                .school(school)
                .schoolClass(schoolClass)
                .joinDate(LocalDateTime.now())
                .build();

        given(userAppQueryService.searchTeacherInfo(anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get(BASE_URL + "/info/teacher")
                                .header("Authorization", "Bearer Access Token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("app-search-teacher-info",
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
                                fieldWithPath("data.schoolClass.schoolYear").type(JsonFieldType.NUMBER)
                                        .description("학년도"),
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
