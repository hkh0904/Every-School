package com.everyschool.userservice.docs.client;

import com.everyschool.userservice.api.controller.client.UserClientController;
import com.everyschool.userservice.api.controller.client.response.UserInfo;
import com.everyschool.userservice.api.controller.user.request.UserIdRequest;
import com.everyschool.userservice.api.controller.user.response.UserClientResponse;
import com.everyschool.userservice.api.service.user.UserQueryService;
import com.everyschool.userservice.docs.RestDocsSupport;
import com.everyschool.userservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserClientControllerDocsTest extends RestDocsSupport {

    private final UserQueryService userQueryService = mock(UserQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);

    @Override
    protected Object initController() {
        return new UserClientController(userQueryService, tokenUtils);
    }

    @DisplayName("회원 PK 단건 조회 API")
    @Test
    void searchUserId() throws Exception {
        UserIdRequest request = UserIdRequest.builder()
            .userKey(UUID.randomUUID().toString())
            .build();

        UserClientResponse response = UserClientResponse.builder()
            .userId(1L)
            .build();

        given(userQueryService.searchUserId(anyString()))
            .willReturn(response);

        mockMvc.perform(
                post("/client/v1/user-id")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("client-search-user-id",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userKey").type(JsonFieldType.STRING)
                        .optional()
                        .description("회원 고유키")
                ),
                responseFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER)
                        .description("회원 PK")
                )
            ));
    }

    @DisplayName("회원 정보 조회 API")
    @Test
    void searchUserInfo() throws Exception {
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
            .andDo(document("client-search-user-info",
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
