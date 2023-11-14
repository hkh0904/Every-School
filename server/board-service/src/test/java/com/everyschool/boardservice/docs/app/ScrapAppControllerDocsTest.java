package com.everyschool.boardservice.docs.app;

import com.everyschool.boardservice.api.app.controller.scrap.ScrapAppController;
import com.everyschool.boardservice.api.app.service.scrap.ScrapAppService;
import com.everyschool.boardservice.docs.RestDocsSupport;
import com.everyschool.boardservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScrapAppControllerDocsTest extends RestDocsSupport {

    private final ScrapAppService scrapAppService = mock(ScrapAppService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/board-service/v1/app/{schoolYear}/schools/{schoolId}/boards/{boardId}/scraps";

    @Override
    protected Object initController() {
        return new ScrapAppController(scrapAppService, tokenUtils);
    }

    @DisplayName("나의 스크랩 등록 API")
    @Test
    void createScrap() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        given(scrapAppService.createScrap(anyString(), anyLong()))
            .willReturn(true);

        mockMvc.perform(
                post(BASE_URL, 2023, 100000, 1)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("create-scrap",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName("Authorization")
                        .description("Bearer Access Token")
                ),
                pathParameters(
                    parameterWithName("schoolYear")
                        .description("학년도"),
                    parameterWithName("schoolId")
                        .description("학교 아이디"),
                    parameterWithName("boardId")
                        .description("게시물 아이디")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.BOOLEAN)
                        .description("응답 데이터")
                )
            ));
    }
}
