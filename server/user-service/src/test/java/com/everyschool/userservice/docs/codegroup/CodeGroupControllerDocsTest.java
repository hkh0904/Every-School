package com.everyschool.userservice.docs.codegroup;

import com.everyschool.userservice.api.controller.codegroup.CodeGroupController;
import com.everyschool.userservice.api.controller.codegroup.request.CreateCodeGroupRequest;
import com.everyschool.userservice.api.controller.codegroup.response.CreateCodeGroupResponse;
import com.everyschool.userservice.api.controller.codegroup.response.RemoveCodeGroupResponse;
import com.everyschool.userservice.api.service.codegroup.CodeGroupService;
import com.everyschool.userservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CodeGroupControllerDocsTest extends RestDocsSupport {

    private final CodeGroupService codeGroupService = mock(CodeGroupService.class);

    @Override
    protected Object initController() {
        return new CodeGroupController(codeGroupService);
    }

    @DisplayName("공통 코드 그룹 등록 API")
    @Test
    void createCodeGroup() throws Exception {
        CreateCodeGroupRequest request = CreateCodeGroupRequest.builder()
            .groupName("직책")
            .build();

        CreateCodeGroupResponse response = CreateCodeGroupResponse.builder()
            .groupId(1)
            .groupName("직책")
            .createdDate(LocalDateTime.now())
            .build();

        given(codeGroupService.createCodeGroup(anyString()))
            .willReturn(response);

        mockMvc.perform(
                post("/v1/code-groups")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-code-group",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("groupName").type(JsonFieldType.STRING)
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
                    fieldWithPath("data.groupName").type(JsonFieldType.STRING)
                        .description("코드 그룹 이름"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("등록 일시")
                )
            ));
    }

    @DisplayName("공통 코드 그룹 삭제 API")
    @Test
    void removeCodeGroup() throws Exception {
        RemoveCodeGroupResponse response = RemoveCodeGroupResponse.builder()
            .groupId(1)
            .groupName("회원구분")
            .removedDate(LocalDateTime.now())
            .build();

        given(codeGroupService.removeCodeGroup(anyInt()))
            .willReturn(response);

        mockMvc.perform(
                delete("/v1/code-groups/{groupId}", 1)
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
                    fieldWithPath("data.groupName").type(JsonFieldType.STRING)
                        .description("코드 그룹 이름"),
                    fieldWithPath("data.removedDate").type(JsonFieldType.ARRAY)
                        .description("삭제 일시")
                )
            ));
    }
}
