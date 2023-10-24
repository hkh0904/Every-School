package com.everyschool.userservice.docs.codedetail;

import com.everyschool.userservice.api.controller.codedetail.CodeDetailController;
import com.everyschool.userservice.api.controller.codedetail.request.CreateCodeDetailRequest;
import com.everyschool.userservice.api.controller.codedetail.respnse.CreateCodeDetailResponse;
import com.everyschool.userservice.api.controller.codedetail.respnse.RemoveCodeDetailResponse;
import com.everyschool.userservice.api.service.codedetail.CodeDetailService;
import com.everyschool.userservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyInt;
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

public class CodeDetailControllerDocsTest extends RestDocsSupport {

    private final CodeDetailService codeDetailService = mock(CodeDetailService.class);

    @Override
    protected Object initController() {
        return new CodeDetailController(codeDetailService);
    }

    @DisplayName("공통 상세 코드 등록 API")
    @Test
    void createCodeDetail() throws Exception {
        CreateCodeDetailRequest request = CreateCodeDetailRequest.builder()
            .codeName("교장")
            .build();

        CreateCodeDetailResponse response = CreateCodeDetailResponse.builder()
            .groupId(1)
            .groupName("직책")
            .codeId(1)
            .codeName("교장")
            .createdDate(LocalDateTime.now())
            .build();

        given(codeDetailService.createCodeDetail(anyInt(), anyString()))
            .willReturn(response);

        mockMvc.perform(
                post("/code-groups/{groupId}/code-details", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-code-detail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("codeName").type(JsonFieldType.STRING)
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
                    fieldWithPath("data.groupId").type(JsonFieldType.NUMBER)
                        .description("코드 그룹 PK"),
                    fieldWithPath("data.groupName").type(JsonFieldType.STRING)
                        .description("코드 그룹 이름"),
                    fieldWithPath("data.codeId").type(JsonFieldType.NUMBER)
                        .description("상세 코드 PK"),
                    fieldWithPath("data.codeName").type(JsonFieldType.STRING)
                        .description("상세 코드 이름"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("등록 일시")
                )
            ));
    }

    @DisplayName("공통 상세 코드 삭제 API")
    @Test
    void removeCodeDetail() throws Exception {
        RemoveCodeDetailResponse response = RemoveCodeDetailResponse.builder()
            .groupId(1)
            .groupName("직책")
            .codeId(1)
            .codeName("교장")
            .removedDate(LocalDateTime.now())
            .build();

        given(codeDetailService.removeCodeDetail(anyInt()))
            .willReturn(response);

        mockMvc.perform(
                delete("/code-groups/{groupId}/code-details/{codeId}", 1, 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-code-detail",
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
                    fieldWithPath("data.codeId").type(JsonFieldType.NUMBER)
                        .description("상세 코드 PK"),
                    fieldWithPath("data.codeName").type(JsonFieldType.STRING)
                        .description("상세 코드 이름"),
                    fieldWithPath("data.removedDate").type(JsonFieldType.ARRAY)
                        .description("삭제 일시")
                )
            ));

    }
}
