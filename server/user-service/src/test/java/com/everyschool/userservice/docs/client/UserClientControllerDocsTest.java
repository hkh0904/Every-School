package com.everyschool.userservice.docs.client;

import com.everyschool.userservice.api.controller.client.UserClientController;
import com.everyschool.userservice.api.controller.client.response.UserInfo;
import com.everyschool.userservice.api.service.user.StudentParentQueryService;
import com.everyschool.userservice.api.service.user.UserQueryService;
import com.everyschool.userservice.docs.RestDocsSupport;
import com.everyschool.userservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.UUID;

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

public class UserClientControllerDocsTest extends RestDocsSupport {

    private final UserQueryService userQueryService = mock(UserQueryService.class);
    private final StudentParentQueryService studentParentQueryService = mock(StudentParentQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);

    @Override
    protected Object initController() {
        return new UserClientController(userQueryService, studentParentQueryService, tokenUtils);
    }

    @DisplayName("토큰으로 회원 정보 조회 API")
    @Test
    void searchUserInfoByToken() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        UserInfo userInfo = UserInfo.builder()
            .userId(1L)
            .userType('S')
            .userName("이예리")
            .schoolClassId(100L)
            .build();

        given(userQueryService.searchUserInfo(anyString()))
            .willReturn(userInfo);

        mockMvc.perform(
                get("/client/v1/user-info")
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("client-search-user-info-token",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER)
                        .description("회원 id"),
                    fieldWithPath("userType").type(JsonFieldType.STRING)
                        .description("회원 타입"),
                    fieldWithPath("userName").type(JsonFieldType.STRING)
                        .description("회원 이름"),
                    fieldWithPath("schoolClassId").type(JsonFieldType.NUMBER)
                        .description("학급 id")
                )
            ));
    }

    @DisplayName("회원 고유키으로 회원 정보 조회 API")
    @Test
    void searchUserInfoByUserKey() throws Exception {
        UserInfo userInfo = UserInfo.builder()
            .userId(1L)
            .userType('S')
            .userName("이예리")
            .schoolClassId(100L)
            .build();

        given(userQueryService.searchUserInfo(anyString()))
            .willReturn(userInfo);

        mockMvc.perform(
                get("/client/v1/user-info/{userKey}", UUID.randomUUID().toString())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("client-search-user-info-key",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER)
                        .description("회원 id"),
                    fieldWithPath("userType").type(JsonFieldType.STRING)
                        .description("회원 타입"),
                    fieldWithPath("userName").type(JsonFieldType.STRING)
                        .description("회원 이름"),
                    fieldWithPath("schoolClassId").type(JsonFieldType.NUMBER)
                        .description("학급 id")
                )
            ));
    }

}
