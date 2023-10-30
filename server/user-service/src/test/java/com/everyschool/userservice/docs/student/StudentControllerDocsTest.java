package com.everyschool.userservice.docs.student;

import com.everyschool.userservice.api.controller.student.StudentController;
import com.everyschool.userservice.api.service.user.StudentService;
import com.everyschool.userservice.docs.RestDocsSupport;
import com.everyschool.userservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentControllerDocsTest extends RestDocsSupport {

    private final StudentService studentService = mock(StudentService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);

    @Override
    protected Object initController() {
        return new StudentController(studentService, tokenUtils);
    }

    @DisplayName("학부모 연결 코드 생성 API")
    @Test
    void generateConnectCode() throws Exception {

        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        given(studentService.generateConnectCode(anyString()))
            .willReturn("d2gHsd34");

        mockMvc.perform(
            get("/v1/connection")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("generate-connect-code",
                preprocessResponse(prettyPrint()),
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
