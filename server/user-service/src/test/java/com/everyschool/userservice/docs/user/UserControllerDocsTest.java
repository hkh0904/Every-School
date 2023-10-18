package com.everyschool.userservice.docs.user;

import com.everyschool.userservice.api.controller.user.UserController;
import com.everyschool.userservice.api.controller.user.request.EditPwdRequest;
import com.everyschool.userservice.api.controller.user.request.ForgotEmailRequest;
import com.everyschool.userservice.api.controller.user.request.ForgotPwdRequest;
import com.everyschool.userservice.api.controller.user.request.JoinUserRequest;
import com.everyschool.userservice.docs.RestDocsSupport;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerDocsTest extends RestDocsSupport {

    @Override
    protected Object initController() {
        return new UserController();
    }

    @DisplayName("회원 가입 API")
    @Test
    void join() throws Exception {
        JoinUserRequest request = JoinUserRequest.builder()
            .userCode(2)
            .email("ssafy@ssafy.com")
            .password("ssafy1234@")
            .name("김싸피")
            .birth("01.01.01")
            .build();

        mockMvc.perform(
                post("/join")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-user",
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

    @DisplayName("이메일 찾기 API")
    @Test
    void forgotEmail() throws Exception {
        ForgotEmailRequest request = ForgotEmailRequest.builder()
            .name("김싸피")
            .birth("010101")
            .build();

        mockMvc.perform(
                post("/forgot")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("forgot-email",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
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
                    fieldWithPath("data").type(JsonFieldType.STRING)
                        .description("응답 데이터")
                )
            ));
    }

    @DisplayName("비밀번호 초기화 API")
    @Test
    void forgotPwd() throws Exception {
        ForgotPwdRequest request = ForgotPwdRequest.builder()
            .email("ssafy@ssafy.com")
            .name("김싸피")
            .birth("010101")
            .build();

        mockMvc.perform(
                post("/forgot/pwd")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("forgot-pwd",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .optional()
                        .description("계정 이메일"),
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
                    fieldWithPath("data").type(JsonFieldType.NULL)
                        .description("응답 데이터")
                )
            ));
    }

    @DisplayName("비밀번호 변경 API")
    @Test
    void editPwd() throws Exception {
        String userKey = UUID.randomUUID().toString();

        EditPwdRequest request = EditPwdRequest.builder()
            .currentPwd("ssafy1234@")
            .newPwd("ssafy5678!")
            .build();

        mockMvc.perform(
                put("/{userKey}/pwd", userKey)
                    .header("Authorization", "Bearer Token")
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
        String userKey = UUID.randomUUID().toString();

        mockMvc.perform(
                delete("/{userKey}/withdrawal", userKey)
                    .header("Authorization", "Bearer Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("delete-user",
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
