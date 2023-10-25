package com.everyschool.userservice.docs.user;

import com.everyschool.userservice.api.controller.user.UserQueryController;
import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.api.service.user.UserQueryService;
import com.everyschool.userservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

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

public class UserQueryControllerDocsTest extends RestDocsSupport {

    private final UserQueryService userQueryService = mock(UserQueryService.class);

    @Override
    protected Object initController() {
        return new UserQueryController(userQueryService);
    }

    @DisplayName("회원 정보 조회 API")
    @Test
    void searchUserInfo() throws Exception {
        UserInfoResponse response = UserInfoResponse.builder()
            .type("학생")
            .email("ssafy@gmail.ccom")
            .name("김싸피")
            .birth("2001-01-01")
            .joinDate(LocalDateTime.now())
            .build();

        given(userQueryService.searchUser(anyString()))
            .willReturn(response);

        mockMvc.perform(
                get("/v1/info")
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-user-info",
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
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("회원 구분"),
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("계정 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("이름"),
                    fieldWithPath("data.birth").type(JsonFieldType.STRING)
                        .description("생년월일"),
                    fieldWithPath("data.joinDate").type(JsonFieldType.ARRAY)
                        .description("가입 일시")
                )
            ));
    }
}
