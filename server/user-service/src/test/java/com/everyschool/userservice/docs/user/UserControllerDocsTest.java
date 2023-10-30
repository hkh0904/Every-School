package com.everyschool.userservice.docs.user;

import com.everyschool.userservice.api.controller.user.UserController;
import com.everyschool.userservice.api.controller.user.request.*;
import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.controller.user.response.WithdrawalResponse;
import com.everyschool.userservice.api.service.user.ParentService;
import com.everyschool.userservice.api.service.user.StudentService;
import com.everyschool.userservice.api.service.user.UserService;
import com.everyschool.userservice.api.service.user.dto.CreateUserDto;
import com.everyschool.userservice.docs.RestDocsSupport;
import com.everyschool.userservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerDocsTest extends RestDocsSupport {

    private final UserService userService = mock(UserService.class);
    private final ParentService parentService = mock(ParentService.class);
    private final StudentService studentService = mock(StudentService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);

    @Override
    protected Object initController() {
        return new UserController(userService, parentService, studentService, tokenUtils);
    }

    @DisplayName("학부모 회원 가입 API")
    @Test
    void joinParent() throws Exception {
        JoinParentRequest request = JoinParentRequest.builder()
            .userCode(2)
            .email("ssafy@ssafy.com")
            .password("ssafy1234@")
            .name("김싸피")
            .birth("1970-01-01")
            .parentType("M")
            .build();

        UserResponse response = UserResponse.builder()
            .email("ssafy@ssafy.com")
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

    @DisplayName("학생 회원 가입 API")
    @Test
    void joinStudent() throws Exception {
        JoinStudentRequest request = JoinStudentRequest.builder()
            .userCode(2)
            .email("ssafy@ssafy.com")
            .password("ssafy1234@")
            .name("김싸피")
            .birth("2001-01-01")
            .build();

        UserResponse response = UserResponse.builder()
            .email("ssafy@ssafy.com")
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

    @DisplayName("비밀번호 변경 API")
    @Test
    void editPwd() throws Exception {
        EditPwdRequest request = EditPwdRequest.builder()
            .currentPwd("ssafy1234@")
            .newPwd("ssafy5678!")
            .build();

        mockMvc.perform(
                patch("/v1/pwd")
                    .header("Authorization", "Bearer Access Token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("edit-pwd",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("currentPwd").type(JsonFieldType.STRING)
                        .optional()
                        .description("현재 비밀번호"),
                    fieldWithPath("newPwd").type(JsonFieldType.STRING)
                        .optional()
                        .description("새 비밀번호")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.NULL)
                        .description("응답 데이터")
                )
            ));
    }

    @DisplayName("회원 탈퇴 API")
    @Test
    void withdrawal() throws Exception {
        WithdrawalRequest request = WithdrawalRequest.builder()
            .pwd("ssafy1234@")
            .build();

        WithdrawalResponse response = WithdrawalResponse.builder()
            .email("ssafy@gmail.com")
            .name("김싸피")
            .type("학생")
            .withdrawalDate(LocalDateTime.now())
            .build();

        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        given(userService.withdrawal(anyString(), anyString()))
            .willReturn(response);

        mockMvc.perform(
                post("/v1/withdrawal")
                    .header("Authorization", "Bearer Access Token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("delete-user",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("pwd").type(JsonFieldType.STRING)
                        .optional()
                        .description("비밀번호")
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
                    fieldWithPath("data.withdrawalDate").type(JsonFieldType.ARRAY)
                        .description("탈퇴 일시")
                )
            ));
    }
}
