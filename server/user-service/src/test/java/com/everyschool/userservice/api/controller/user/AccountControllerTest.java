package com.everyschool.userservice.api.controller.user;

import com.everyschool.userservice.ControllerTestSupport;
import com.everyschool.userservice.api.controller.user.request.ForgotEmailRequest;
import com.everyschool.userservice.api.controller.user.request.ForgotPwdRequest;
import com.everyschool.userservice.api.service.user.UserQueryService;
import com.everyschool.userservice.api.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
class AccountControllerTest extends ControllerTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserQueryService userQueryService;

    @DisplayName("계정 이메일을 찾을 때 이름은 필수값이다.")
    @Test
    void forgotEmailWithoutName() throws Exception {
        //given
        ForgotEmailRequest request = ForgotEmailRequest.builder()
            .birth("2001-01-01")
            .build();

        //when //then
        mockMvc.perform(
                post("/forgot/email")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이름은 필수입니다."));
    }

    @DisplayName("계정 이메일을 찾을 때 생년월일은 필수값이다.")
    @Test
    void forgotEmailWithoutBirth() throws Exception {
        //given
        ForgotEmailRequest request = ForgotEmailRequest.builder()
            .name("김싸피")
            .build();

        //when //then
        mockMvc.perform(
                post("/forgot/email")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("생년월일은 필수입니다."));
    }

    @DisplayName("계정 이메일을 조회한다.")
    @Test
    void forgotEmail() throws Exception {
        //given
        ForgotEmailRequest request = ForgotEmailRequest.builder()
            .name("김싸피")
            .birth("2001-01-01")
            .build();

        //when //then
        mockMvc.perform(
                post("/forgot/email")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }

    @DisplayName("계정 비밀번호를 찾을 때 이메일은 필수값이다.")
    @Test
    void forgotPwdWithoutEmail() throws Exception {
        //given
        ForgotPwdRequest request = ForgotPwdRequest.builder()
            .name("김싸피")
            .birth("2001-01-01")
            .build();

        //when //then
        mockMvc.perform(
                post("/forgot/pwd")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이메일은 필수입니다."));
    }

    @DisplayName("계정 비밀번호를 찾을 때 이름은 필수값이다.")
    @Test
    void forgotPwdWithoutName() throws Exception {
        //given
        ForgotPwdRequest request = ForgotPwdRequest.builder()
            .email("ssafy@gmail.com")
            .birth("2001-01-01")
            .build();

        //when //then
        mockMvc.perform(
                post("/forgot/pwd")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이름은 필수입니다."));
    }

    @DisplayName("계정 비밀번호를 찾을 때 생년월일은 필수값이다.")
    @Test
    void forgotPwdWithoutBirth() throws Exception {
        //given
        ForgotPwdRequest request = ForgotPwdRequest.builder()
            .email("ssafy@gmail.com")
            .name("김싸피")
            .build();

        //when //then
        mockMvc.perform(
                post("/forgot/pwd")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("생년월일은 필수입니다."));
    }

    @DisplayName("계정 비밀번호를 초기화한다.")
    @Test
    void forgotPwd() throws Exception {
        //given
        ForgotPwdRequest request = ForgotPwdRequest.builder()
            .email("ssafy@gmail.com")
            .name("김싸피")
            .birth("2001-01-01")
            .build();

        //when //then
        mockMvc.perform(
                post("/forgot/pwd")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("비밀번호가 초기화 되었습니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }
}