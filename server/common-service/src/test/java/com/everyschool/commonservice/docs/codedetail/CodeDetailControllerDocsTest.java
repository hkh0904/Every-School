package com.everyschool.commonservice.docs.codedetail;

import com.everyschool.commonservice.api.controller.codedetail.CodeDetailController;
import com.everyschool.commonservice.api.controller.codedetail.request.CreateCodeDetailRequest;
import com.everyschool.commonservice.docs.RestDocsSupport;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CodeDetailControllerDocsTest extends RestDocsSupport {

    @Override
    protected Object initController() {
        return new CodeDetailController();
    }

    @DisplayName("상세 코드 등록 API")
    @Test
    void createCodeDetail() throws Exception {
        CreateCodeDetailRequest request = CreateCodeDetailRequest.builder()
            .name("교장")
            .build();

        mockMvc.perform(
            post("/common-service/groups/{groupId}/codes", 1L)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-code-detail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .optional()
                        .description("상세 코드 이름")
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
                    fieldWithPath("data.codeId").type(JsonFieldType.NUMBER)
                        .description("상세 코드 PK"),
                    fieldWithPath("data.groupName").type(JsonFieldType.STRING)
                        .description("코드 그룹 이름"),
                    fieldWithPath("data.codeName").type(JsonFieldType.STRING)
                        .description("상세 코드 이름"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("등록 일시")
                )
            ));
    }
}
