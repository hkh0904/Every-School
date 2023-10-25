package com.everyschool.userservice.docs.user;

import com.everyschool.userservice.api.controller.user.AccountController;
import com.everyschool.userservice.api.controller.user.request.ForgotEmailRequest;
import com.everyschool.userservice.api.service.user.UserQueryService;
import com.everyschool.userservice.api.service.user.UserService;
import com.everyschool.userservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.anyString;
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

public class AccountControllerDocsTest extends RestDocsSupport {

    private final UserService userService = mock(UserService.class);
    private final UserQueryService userQueryService = mock(UserQueryService.class);

    @Override
    protected Object initController() {
        return new AccountController(userService, userQueryService);
    }

    @DisplayName("이메일 찾기 API")
    @Test
    void forgotEmail() throws Exception {
        ForgotEmailRequest request = ForgotEmailRequest.builder()
            .name("김싸피")
            .birth("010101")
            .build();

        given(userQueryService.searchEmail(anyString(), anyString()))
            .willReturn("ssa**@gmail.com");

        mockMvc.perform(
                post("/forgot/email")
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
}
