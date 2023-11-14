package com.everyschool.boardservice.docs.app;

import com.everyschool.boardservice.api.SliceResponse;
import com.everyschool.boardservice.api.app.controller.scrap.ScrapAppQueryController;
import com.everyschool.boardservice.api.app.controller.scrap.response.MyScrapResponse;
import com.everyschool.boardservice.api.app.service.scrap.ScrapAppQueryService;
import com.everyschool.boardservice.docs.RestDocsSupport;
import com.everyschool.boardservice.domain.board.Category;
import com.everyschool.boardservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.everyschool.boardservice.domain.board.Category.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScrapAppQueryControllerDocsTest extends RestDocsSupport {

    private final ScrapAppQueryService scrapAppQueryService = mock(ScrapAppQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/board-service/v1/app/{schoolYear}/schools/{schoolId}/scraps";

    @Override
    protected Object initController() {
        return new ScrapAppQueryController(scrapAppQueryService, tokenUtils);
    }

    @DisplayName("나의 스크랩 조회 API")
    @Test
    void searchMyScrap() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        MyScrapResponse response = MyScrapResponse.builder()
            .boardId(1L)
            .type(FREE.getCode())
            .title("이예리는 카페리아를 좋아해요!")
            .content("카페리아 커피 너무 작던데...")
            .commentCount(3)
            .createdDate(LocalDateTime.now())
            .build();

        PageRequest pageRequest = PageRequest.of(1, 5);

        SliceImpl<MyScrapResponse> responses = new SliceImpl<>(List.of(response), pageRequest, false);

        given(scrapAppQueryService.searchMyScrap(anyString(), any()))
            .willReturn(new SliceResponse<>(responses));

        mockMvc.perform(
                get(BASE_URL, 2023, 100000)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-my-scrap",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName("Authorization")
                        .description("Bearer Access Token")
                ),
                pathParameters(
                    parameterWithName("schoolYear")
                        .description("학년도"),
                    parameterWithName("schoolId")
                        .description("학교 아이디")
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
                    fieldWithPath("data.content[].boardId").type(JsonFieldType.NUMBER)
                        .description("게시글 아이디"),
                    fieldWithPath("data.content[].type").type(JsonFieldType.STRING)
                        .description("게시글 카테고리"),
                    fieldWithPath("data.content[].title").type(JsonFieldType.STRING)
                        .description("게시글 제목"),
                    fieldWithPath("data.content[].content").type(JsonFieldType.STRING)
                        .description("게시글 내용"),
                    fieldWithPath("data.content[].commentCount").type(JsonFieldType.NUMBER)
                        .description("게시글에 달린 댓글수"),
                    fieldWithPath("data.content[].createdDate").type(JsonFieldType.ARRAY)
                        .description("게시글 작성일"),
                    fieldWithPath("data.content[].isTapped").type(JsonFieldType.BOOLEAN)
                        .description("탭 여부"),
                    fieldWithPath("data.currentPage").type(JsonFieldType.NUMBER)
                        .description("현재 페이지 번호"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("데이터 요청 갯수"),
                    fieldWithPath("data.isFirst").type(JsonFieldType.BOOLEAN)
                        .description("첫 페이지 여부"),
                    fieldWithPath("data.isLast").type(JsonFieldType.BOOLEAN)
                        .description("마지막 페이지 여부")
                )
            ));
    }
}
