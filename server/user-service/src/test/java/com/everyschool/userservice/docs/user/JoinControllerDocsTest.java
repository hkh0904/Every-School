package com.everyschool.userservice.docs.user;

import com.everyschool.userservice.api.controller.user.JoinController;
import com.everyschool.userservice.api.controller.user.request.JoinParentRequest;
import com.everyschool.userservice.api.controller.user.request.JoinStudentRequest;
import com.everyschool.userservice.api.controller.user.request.JoinTeacherRequest;
import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.service.user.ParentService;
import com.everyschool.userservice.api.service.user.StudentService;
import com.everyschool.userservice.api.service.user.TeacherService;
import com.everyschool.userservice.api.service.user.dto.CreateUserDto;
import com.everyschool.userservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JoinControllerDocsTest extends RestDocsSupport {

    private final ParentService parentService = mock(ParentService.class);
    private final StudentService studentService = mock(StudentService.class);
    private final TeacherService teacherService = mock(TeacherService.class);

    @Override
    protected Object initController() {
        return new JoinController(parentService, studentService, teacherService);
    }

    @DisplayName("학생 회원 가입 API")
    @Test
    void joinStudent() throws Exception {
        JoinStudentRequest request = JoinStudentRequest.builder()
            .userCode(1001)
            .email("student@gmail.com")
            .password("ssafy1234@")
            .name("김싸피")
            .birth("2001-01-01")
            .build();

        UserResponse response = UserResponse.builder()
            .email("student@gmail.com")
            .name("김싸피")
            .type("학생")
            .createdDate(LocalDateTime.now())
            .build();

        given(studentService.createStudent(any(CreateUserDto.class)))
            .willReturn(response);

        mockMvc.perform(
                post("/join/student")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-student",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userCode").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("계정 이메일"),
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .optional()
                        .description("계정 이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING)
                        .optional()
                        .description("계정 비밀번호"),
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .optional()
                        .description("이름"),
                    fieldWithPath("birth").type(JsonFieldType.STRING)
                        .optional()
                        .description("생년월일")
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
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("계정 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("이름"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("회원 유형"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("가입 일시")
                )
            ));
    }

    @DisplayName("학부모 회원 가입 API")
    @Test
    void joinParent() throws Exception {
        JoinParentRequest request = JoinParentRequest.builder()
            .userCode(1002)
            .email("parent@gmail.com")
            .password("ssafy1234@")
            .name("김싸피")
            .birth("1970-01-01")
            .parentType("M")
            .build();

        UserResponse response = UserResponse.builder()
            .email("parent@gmail.com")
            .name("김싸피")
            .type("학부모")
            .createdDate(LocalDateTime.now())
            .build();

        given(parentService.createParent(any(CreateUserDto.class), anyString()))
            .willReturn(response);

        mockMvc.perform(
                post("/join/parent")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-parent",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userCode").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("계정 이메일"),
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .optional()
                        .description("계정 이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING)
                        .optional()
                        .description("계정 비밀번호"),
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .optional()
                        .description("이름"),
                    fieldWithPath("birth").type(JsonFieldType.STRING)
                        .optional()
                        .description("생년월일"),
                    fieldWithPath("parentType").type(JsonFieldType.STRING)
                        .optional()
                        .description("부모타입")
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
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("계정 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("이름"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("회원 유형"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("가입 일시")
                )
            ));
    }

    @DisplayName("교직원 회원 가입 API")
    @Test
    void joinTeacher() throws Exception {
        JoinTeacherRequest request = JoinTeacherRequest.builder()
            .userCode(1003)
            .email("teacher@gmail.com")
            .password("ssafy1234@")
            .name("김싸피")
            .birth("1990-01-01")
            .build();

        UserResponse response = UserResponse.builder()
            .email("teacher@gmail.com")
            .name("김싸피")
            .type("교직원")
            .createdDate(LocalDateTime.now())
            .build();

        given(teacherService.createTeacher(any(CreateUserDto.class)))
            .willReturn(response);

        mockMvc.perform(
                post("/join/teacher")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-teacher",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userCode").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("계정 이메일"),
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .optional()
                        .description("계정 이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING)
                        .optional()
                        .description("계정 비밀번호"),
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .optional()
                        .description("이름"),
                    fieldWithPath("birth").type(JsonFieldType.STRING)
                        .optional()
                        .description("생년월일")
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
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("계정 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("이름"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("회원 유형"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("가입 일시")
                )
            ));
    }
}
