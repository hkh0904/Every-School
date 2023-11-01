package com.everyschool.boardservice.docs.board;

import com.everyschool.boardservice.api.controller.board.BoardQueryController;
import com.everyschool.boardservice.api.controller.board.response.NewFreeBoardResponse;
import com.everyschool.boardservice.api.controller.board.response.NewNoticeResponse;
import com.everyschool.boardservice.api.service.board.BoardQueryService;
import com.everyschool.boardservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
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

public class BoardQueryControllerDocsTest extends RestDocsSupport {

    private final BoardQueryService boardQueryService = mock(BoardQueryService.class);

    @Override
    protected Object initController() {
        return new BoardQueryController(boardQueryService);
    }

    @DisplayName("자유 게시판 새 글 조회 API")
    @Test
    void searchNewFreeBoards() throws Exception {
        NewFreeBoardResponse response1 = createNewFreeBoardResponse(5L, "김인호 나마에와");
        NewFreeBoardResponse response2 = createNewFreeBoardResponse(4L, "리온이 애교");
        NewFreeBoardResponse response3 = createNewFreeBoardResponse(3L, "신한은행 면접");
        NewFreeBoardResponse response4 = createNewFreeBoardResponse(2L, "페이커 우승 가능할까?");
        NewFreeBoardResponse response5 = createNewFreeBoardResponse(1L, "롤토체스 티어댁");

        given(boardQueryService.searchNewFreeBoards(anyLong()))
            .willReturn(List.of(response1, response2, response3, response4, response5));

        mockMvc.perform(
            get("/board-service/v1/schools/{schoolId}/boards/new-free", 1L)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-new-free-boards",
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
                    fieldWithPath("data[].boardId").type(JsonFieldType.NUMBER)
                        .description("게시물 id"),
                    fieldWithPath("data[].title").type(JsonFieldType.STRING)
                        .description("게시물 제목")
                )
            ));
    }

    @DisplayName("공지사항 새 글 조회 API")
    @Test
    void searchNewNoticeBoards() throws Exception {
        NewNoticeResponse response4 = createNewNoticeResponse(4L, "가정통신문 제목 4");
        NewNoticeResponse response3 = createNewNoticeResponse(3L, "가정통신문 제목 3");
        NewNoticeResponse response2 = createNewNoticeResponse(2L, "가정통신문 제목 2");
        NewNoticeResponse response1 = createNewNoticeResponse(1L, "가정통신문 제목 1");

        given(boardQueryService.searchNewNoticeBoards(anyLong()))
            .willReturn(List.of(response4, response3, response2, response1));

        mockMvc.perform(
                get("/board-service/v1/schools/{schoolId}/boards/new-notice", 1L)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-new-notice-boards",
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
                    fieldWithPath("data[].boardId").type(JsonFieldType.NUMBER)
                        .description("게시물 id"),
                    fieldWithPath("data[].title").type(JsonFieldType.STRING)
                        .description("게시물 제목"),
                    fieldWithPath("data[].createdDate").type(JsonFieldType.ARRAY)
                        .description("작성일")
                )
            ));
    }

    private NewNoticeResponse createNewNoticeResponse(long boardId, String title) {
        return NewNoticeResponse.builder()
            .boardId(boardId)
            .title(title)
            .createdDate(LocalDateTime.now())
            .build();
    }

    private NewFreeBoardResponse createNewFreeBoardResponse(long boardId, String title) {
        return NewFreeBoardResponse.builder()
            .boardId(boardId)
            .title(title)
            .build();
    }
}
