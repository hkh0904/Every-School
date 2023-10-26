package com.everyschool.userservice.docs.codedetail;

import com.everyschool.userservice.api.controller.codedetail.CodeDetailQueryController;
import com.everyschool.userservice.api.controller.codedetail.respnse.CodeDetailResponse;
import com.everyschool.userservice.api.controller.codedetail.respnse.CodeResponse;
import com.everyschool.userservice.api.service.codedetail.CodeDetailQueryService;
import com.everyschool.userservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CodeDetailQueryControllerDocsTest extends RestDocsSupport {

    private final CodeDetailQueryService codeDetailQueryService = mock(CodeDetailQueryService.class);

    @Override
    protected Object initController() {
        return new CodeDetailQueryController(codeDetailQueryService);
    }

    @DisplayName("공통 상세 코드 전체 조회 API")
    @Test
    void searchCodeDetails() throws Exception {
        CodeDetailResponse response1 = createCodeDetailResponse(1, "교장");
        CodeDetailResponse response2 = createCodeDetailResponse(2, "교감");
        CodeDetailResponse response3 = createCodeDetailResponse(3, "담임");

        CodeResponse response = CodeResponse.builder()
            .groupId(1)
            .groupName("직책")
            .codes(List.of(response1, response2, response3))
            .build();

        given(codeDetailQueryService.searchCodeDetails(anyInt()))
            .willReturn(response);

        mockMvc.perform(
                get("/v1/code-groups/{groupId}/code-details", 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-code-detail",
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
                        .description("코드 그룹 ID"),
                    fieldWithPath("data.groupName").type(JsonFieldType.STRING)
                        .description("코드 그룹 이름"),
                    fieldWithPath("data.codes").type(JsonFieldType.ARRAY)
                        .description("코드 데이터"),
                    fieldWithPath("data.codes[].codeId").type(JsonFieldType.NUMBER)
                        .description("코드 ID"),
                    fieldWithPath("data.codes[].codeName").type(JsonFieldType.STRING)
                        .description("코드 이름"),
                    fieldWithPath("data.codes[].isDeleted").type(JsonFieldType.BOOLEAN)
                        .description("코드 삭제 여부")
                )
            ));
    }

    private CodeDetailResponse createCodeDetailResponse(int codeId, String codeName) {
        return CodeDetailResponse.builder()
            .codeId(codeId)
            .codeName(codeName)
            .isDeleted(false)
            .build();
    }
}
