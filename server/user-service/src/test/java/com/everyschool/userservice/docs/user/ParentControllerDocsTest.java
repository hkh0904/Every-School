package com.everyschool.userservice.docs.user;

import com.everyschool.userservice.api.controller.user.ParentController;
import com.everyschool.userservice.api.controller.user.request.ConnectStudentParentRequest;
import com.everyschool.userservice.api.controller.user.response.CreateStudentParentResponse;
import com.everyschool.userservice.api.service.user.StudentParentService;
import com.everyschool.userservice.docs.RestDocsSupport;
import com.everyschool.userservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
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

public class ParentControllerDocsTest extends RestDocsSupport {

    private final StudentParentService studentParentService = mock(StudentParentService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);

    @Override
    protected Object initController() {
        return new ParentController(studentParentService, tokenUtils);
    }

    @DisplayName("학부모 연결 코드 입력 API")
    @Test
    void connectStudentParent() throws Exception {
        ConnectStudentParentRequest request = ConnectStudentParentRequest.builder()
            .connectCode("d2gHsd34")
            .build();

        CreateStudentParentResponse response = CreateStudentParentResponse.builder()
            .parentType("모")
            .parentName("이예리")
            .studentName("리온")
            .createdDate(LocalDateTime.now())
            .build();

        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        given(studentParentService.createStudentParent(anyString(), anyString()))
            .willReturn(response);

        mockMvc.perform(
            post("/v1/connection")
                .header("Authorization", "Bearer Access Token")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-student-parent",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("connectCode").type(JsonFieldType.STRING)
                        .optional()
                        .description("자녀 연결 코드")
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
                    fieldWithPath("data.parentType").type(JsonFieldType.STRING)
                        .description("부모 타입"),
                    fieldWithPath("data.parentName").type(JsonFieldType.STRING)
                        .description("부모 이름"),
                    fieldWithPath("data.studentName").type(JsonFieldType.STRING)
                        .description("학생 이름"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("등록 일시")
                )
            ));
    }
}
