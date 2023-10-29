package com.everyschool.userservice.docs.user;

import com.everyschool.userservice.api.controller.user.UserClientController;
import com.everyschool.userservice.api.controller.user.request.UserIdRequest;
import com.everyschool.userservice.api.controller.user.response.UserClientResponse;
import com.everyschool.userservice.api.service.user.UserQueryService;
import com.everyschool.userservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.UUID;

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

public class UserClientControllerDocsTest extends RestDocsSupport {

    private final UserQueryService userQueryService = mock(UserQueryService.class);

    @Override
    protected Object initController() {
        return new UserClientController(userQueryService);
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
}
