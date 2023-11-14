package com.everyschool.boardservice.docs.app;

import com.everyschool.boardservice.api.app.controller.board.MyBoardAppController;
import com.everyschool.boardservice.api.app.controller.board.response.BoardResponse;
import com.everyschool.boardservice.api.app.service.board.MyBoardAppQueryService;
import com.everyschool.boardservice.docs.RestDocsSupport;
import com.everyschool.boardservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MyBoardAppControllerDocsTest extends RestDocsSupport {

    private final MyBoardAppQueryService myBoardAppQueryService = mock(MyBoardAppQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/board-service/v1/app/{schoolYear}/schools/{schoolId}/my";

    @Override
    protected Object initController() {
        return new MyBoardAppController(myBoardAppQueryService, tokenUtils);
    }

    @DisplayName("나의 작성 글 조회 API")
    @Test
    void myBoards() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        BoardResponse response = BoardResponse.builder()
            .boardId(1L)
            .title("너무 힘들어요...")
            .content("API가 너무 많아요...")
            .commentCount(3)
            .scrapCount(0)
            .createdDate(LocalDateTime.now())
            .build();

        given(myBoardAppQueryService.myBoards(anyString(), anyInt()))
            .willReturn(List.of(response));

        mockMvc.perform(
                get(BASE_URL + "/boards", 2023, 100000)
                    .param("category", "6001")
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("app-my-boards",
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
                requestParameters(
                    parameterWithName("category")
                        .description("카테고리 코드")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터"),
                    fieldWithPath("data[].boardId").type(JsonFieldType.NUMBER)
                        .description("게시물 아이디"),
                    fieldWithPath("data[].title").type(JsonFieldType.STRING)
                        .description("게시물 제목"),
                    fieldWithPath("data[].content").type(JsonFieldType.STRING)
                        .description("게시물 내용"),
                    fieldWithPath("data[].commentCount").type(JsonFieldType.NUMBER)
                        .description("게시물 댓글 수"),
                    fieldWithPath("data[].scrapCount").type(JsonFieldType.NUMBER)
                        .description("게시물 스크랩 수"),
                    fieldWithPath("data[].createdDate").type(JsonFieldType.ARRAY)
                        .description("게시물 작성 일시"),
                    fieldWithPath("data[].isTapped").type(JsonFieldType.BOOLEAN)
                        .description("탭 여부")
                )
            ));
    }

    @DisplayName("나의 댓글 작성 게시물 조회 API")
    @Test
    void myComments() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        BoardResponse response = BoardResponse.builder()
            .boardId(1L)
            .title("너무 힘들어요...")
            .content("API가 너무 많아요...")
            .commentCount(3)
            .scrapCount(0)
            .createdDate(LocalDateTime.now())
            .build();

        given(myBoardAppQueryService.myBoards(anyString(), anyInt()))
            .willReturn(List.of(response));

        mockMvc.perform(
                get(BASE_URL + "/boards", 2023, 100000)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("app-my-comments",
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
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터"),
                    fieldWithPath("data[].boardId").type(JsonFieldType.NUMBER)
                        .description("게시물 아이디"),
                    fieldWithPath("data[].title").type(JsonFieldType.STRING)
                        .description("게시물 제목"),
                    fieldWithPath("data[].content").type(JsonFieldType.STRING)
                        .description("게시물 내용"),
                    fieldWithPath("data[].commentCount").type(JsonFieldType.NUMBER)
                        .description("게시물 댓글 수"),
                    fieldWithPath("data[].scrapCount").type(JsonFieldType.NUMBER)
                        .description("게시물 스크랩 수"),
                    fieldWithPath("data[].createdDate").type(JsonFieldType.ARRAY)
                        .description("게시물 작성 일시"),
                    fieldWithPath("data[].isTapped").type(JsonFieldType.BOOLEAN)
                        .description("탭 여부")
                )
            ));
    }
}
