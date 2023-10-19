package com.everyschool.commonservice.docs.codegroup;

import com.everyschool.commonservice.api.controller.codegroup.CodeGroupController;
import com.everyschool.commonservice.api.controller.codegroup.request.CreateCodeGroupRequest;
import com.everyschool.commonservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CodeGroupControllerDocsTest extends RestDocsSupport {

    @Override
    protected Object initController() {
        return new CodeGroupController();
    }

    @DisplayName("코드 그룹 등록 API")
    @Test
    void createCodeGroup() throws Exception {
        CreateCodeGroupRequest request = CreateCodeGroupRequest.builder()
            .name("직책")
            .build();

        mockMvc.perform(
            post("/common-service/groups")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-code-group",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .optional()
                        .description("코드 그룹 이름")
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
                    fieldWithPath("data.groupId").type(JsonFieldType.NUMBER)
                        .description("코드 그룹 PK"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("코드 그룹 이름"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("등록 일시")
                )
            ));
    }

    @DisplayName("코드 그룹 목록 조회 API")
    @Test
    void searchCodeGroups() throws Exception {
        mockMvc.perform(
            get("/common-service/groups")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-code-group",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터"),
                    fieldWithPath("data[].groupId").type(JsonFieldType.NUMBER)
                        .description("코드 그룹 PK"),
                    fieldWithPath("data[].name").type(JsonFieldType.STRING)
                        .description("코드 그룹 이름")
                )
            ));
    }

    @DisplayName("코드 그룹 삭제 API")
    @Test
    void removeCodeGroup() throws Exception {
        mockMvc.perform(
            delete("/common-service/groups/{groupId}", 1L)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-code-group",
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
                    fieldWithPath("data.groupId").type(JsonFieldType.NUMBER)
                        .description("코드 그룹 PK"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("코드 그룹 이름"),
                    fieldWithPath("data.removedDate").type(JsonFieldType.ARRAY)
                        .description("삭제 일시")
                )
            ));
    }
}
