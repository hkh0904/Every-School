package com.everyschool.userservice.docs.codegroup;

import com.everyschool.userservice.api.controller.codegroup.CodeGroupQueryController;
import com.everyschool.userservice.api.controller.codegroup.response.CodeGroupResponse;
import com.everyschool.userservice.api.service.codegroup.CodeGroupQueryService;
import com.everyschool.userservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

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

public class CodeGroupQueryControllerDocsTest extends RestDocsSupport {

    private final CodeGroupQueryService codeGroupQueryService = mock(CodeGroupQueryService.class);

    @Override
    protected Object initController() {
        return new CodeGroupQueryController(codeGroupQueryService);
    }

    @DisplayName("공통 코드 그룹 전체 조회 API")
    @Test
    void searchCodeGroups() throws Exception {
        CodeGroupResponse response1 = createCodeGroupResponse(1, "회원구분");
        CodeGroupResponse response2 = createCodeGroupResponse(2, "직책");
        CodeGroupResponse response3 = createCodeGroupResponse(3, "카테고리");

        given(codeGroupQueryService.searchCodeGroups())
            .willReturn(List.of(response1, response2, response3));

        mockMvc.perform(
            get("/code-groups")
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
                        .description("응답 데이터"),
                    fieldWithPath("data[].groupName").type(JsonFieldType.STRING)
                        .description("응답 데이터"),
                    fieldWithPath("data[].isDeleted").type(JsonFieldType.BOOLEAN)
                        .description("응답 데이터")
                )
            ));
    }

    private CodeGroupResponse createCodeGroupResponse(int groupId, String groupName) {
        return CodeGroupResponse.builder()
            .groupId(groupId)
            .groupName(groupName)
            .isDeleted(false)
            .build();
    }
}
