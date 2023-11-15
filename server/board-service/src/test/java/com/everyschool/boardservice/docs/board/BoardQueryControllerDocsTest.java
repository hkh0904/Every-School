package com.everyschool.boardservice.docs.board;

import com.everyschool.boardservice.api.SliceResponse;
import com.everyschool.boardservice.api.app.controller.board.BoardAppQueryController;
import com.everyschool.boardservice.api.app.controller.board.response.*;
import com.everyschool.boardservice.api.app.service.board.BoardAppQueryService;
import com.everyschool.boardservice.docs.RestDocsSupport;
import com.everyschool.boardservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoardQueryControllerDocsTest extends RestDocsSupport {

    private final BoardAppQueryService boardQueryService = mock(BoardAppQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/board-service/v1/app/{schoolYear}/schools/{schoolId}";

    @Override
    protected Object initController() {
        return new BoardAppQueryController(boardQueryService, tokenUtils);
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
                        get(BASE_URL + "/free-boards/new", 2023, 100000)
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
        NewNoticeResponse response4 = createNewNoticeResponse(4L, "공지사항 제목 4");
        NewNoticeResponse response3 = createNewNoticeResponse(3L, "공지사항 제목 3");
        NewNoticeResponse response2 = createNewNoticeResponse(2L, "공지사항 제목 2");
        NewNoticeResponse response1 = createNewNoticeResponse(1L, "공지사항 제목 1");

        given(boardQueryService.searchNewNoticeBoards(anyLong()))
                .willReturn(List.of(response4, response3, response2, response1));

        mockMvc.perform(
                        get(BASE_URL + "/notice-boards/new", 2023, 100000)
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

    @DisplayName("가정통신문 새 글 조회 API")
    @Test
    void searchNewCommunicationBoards() throws Exception {
        NewCommunicationResponse response3 = createNewCommunicationResponse(3L, "가정통신문 제목 3");
        NewCommunicationResponse response2 = createNewCommunicationResponse(2L, "가정통신문 제목 2");
        NewCommunicationResponse response1 = createNewCommunicationResponse(1L, "가정통신문 제목 1");

        given(boardQueryService.searchNewCommunicationBoards(anyLong()))
                .willReturn(List.of(response3, response2, response1));

        mockMvc.perform(
                        get(BASE_URL + "/communication-boards/new", 2023, 100000)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-new-communication-boards",
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

    @DisplayName("자유 게시판 목록 조회 API")
    @Test
    void searchFreeBoards() throws Exception {
        BoardResponse response3 = createBoardResponse(3L, "자유 게시판 제목 3", "자유 게시판 내용 3", 10);
        BoardResponse response2 = createBoardResponse(2L, "자유 게시판 제목 2", "자유 게시판 내용 2", 0);
        BoardResponse response1 = createBoardResponse(1L, "자유 게시판 제목 1", "자유 게시판 내용 1", 3);

        PageRequest pageRequest = PageRequest.of(0, 10);
        SliceImpl<BoardResponse> responses = new SliceImpl<>(List.of(response1, response2, response3), pageRequest, false);
        SliceResponse<BoardResponse> response = new SliceResponse<>(responses);
        given(boardQueryService.searchFreeBoards(anyLong(), any()))
                .willReturn(response);

        mockMvc.perform(
                        get(BASE_URL + "/free-boards", 2023, 100000)
                                .param("page", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-free-boards",
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page")
                                        .description("페이지 번호")
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
                                        .description("게시글 id"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING)
                                        .description("게시글 제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING)
                                        .description("게시글 내용"),
                                fieldWithPath("data.content[].commentCount").type(JsonFieldType.NUMBER)
                                        .description("게시글에 달린 댓글수"),
                                fieldWithPath("data.content[].scrapCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 스크랩 수"),
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

    @DisplayName("공지사항 목록 조회 API")
    @Test
    void searchNoticeBoards() throws Exception {
        BoardResponse response2 = createBoardResponse(2L, "공지사항 제목 2", "공지사항 내용 2", 10);
        BoardResponse response1 = createBoardResponse(1L, "공지사항 제목 1", "공지사항 내용 1", 0);

        PageRequest pageRequest = PageRequest.of(0, 10);
        SliceImpl<BoardResponse> responses = new SliceImpl<>(List.of(response1, response2), pageRequest, false);
        SliceResponse<BoardResponse> response = new SliceResponse<>(responses);
        given(boardQueryService.searchNoticeBoards(anyLong(), any()))
                .willReturn(response);

        mockMvc.perform(
                        get(BASE_URL + "/notice-boards", 2023, 100000)
                                .param("page", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-notice-boards",
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page")
                                        .description("페이지 번호")
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
                                        .description("게시글 id"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING)
                                        .description("게시글 제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING)
                                        .description("게시글 내용"),
                                fieldWithPath("data.content[].commentCount").type(JsonFieldType.NUMBER)
                                        .description("게시글에 달린 댓글수"),
                                fieldWithPath("data.content[].scrapCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 스크랩 수"),
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

    @DisplayName("가정통신문 목록 조회 API")
    @Test
    void searchCommunicationBoards() throws Exception {
        BoardResponse response2 = createBoardResponse(2L, "가정통신문 제목 2", "가정통신문 내용 2", 10);
        BoardResponse response1 = createBoardResponse(1L, "가정통신문 제목 1", "가정통신문 내용 1", 0);

        PageRequest pageRequest = PageRequest.of(0, 10);
        SliceImpl<BoardResponse> responses = new SliceImpl<>(List.of(response1, response2), pageRequest, false);
        SliceResponse<BoardResponse> response = new SliceResponse<>(responses);
        given(boardQueryService.searchCommunicationBoards(anyLong(), any()))
                .willReturn(response);

        mockMvc.perform(
                        get(BASE_URL + "/communication-boards", 2023, 100000)
                                .param("page", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-communication-boards",
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page")
                                        .description("페이지 번호")
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
                                        .description("게시글 id"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING)
                                        .description("게시글 제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING)
                                        .description("게시글 내용"),
                                fieldWithPath("data.content[].commentCount").type(JsonFieldType.NUMBER)
                                        .description("게시글에 달린 댓글수"),
                                fieldWithPath("data.content[].scrapCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 스크랩 수"),
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

    @DisplayName("자유 게시판 상세 조회 API")
    @Test
    void searchFreeBoard() throws Exception {
        given(tokenUtils.getUserKey())
                .willReturn(UUID.randomUUID().toString());

        FreeBoardDetailResponse.ReCommentVo reComment1 = FreeBoardDetailResponse.ReCommentVo.builder()
                .commentId(2L)
                .sender(0)
                .content("내년에 같이가요!")
                .depth(2)
                .isMine(true)
                .createdDate(LocalDateTime.now())
                .build();
        FreeBoardDetailResponse.ReCommentVo reComment2 = FreeBoardDetailResponse.ReCommentVo.builder()
                .commentId(3L)
                .sender(1)
                .content("좋아요!")
                .depth(2)
                .isMine(false)
                .createdDate(LocalDateTime.now())
                .build();

        FreeBoardDetailResponse.CommentVo comment1 = FreeBoardDetailResponse.CommentVo.builder()
                .commentId(1L)
                .sender(1)
                .content("저도 가고 싶어요ㅜㅜ")
                .depth(1)
                .isMine(false)
                .createdDate(LocalDateTime.now())
                .reComments(List.of(reComment1, reComment2))
                .build();

        FreeBoardDetailResponse.CommentVo comment2 = FreeBoardDetailResponse.CommentVo.builder()
                .commentId(4L)
                .sender(2)
                .content("저는 12월에 올나잇 갈꺼에요!")
                .depth(1)
                .isMine(false)
                .createdDate(LocalDateTime.now())
                .reComments(new ArrayList<>())
                .build();

        FreeBoardDetailResponse response = FreeBoardDetailResponse.builder()
                .boardId(1L)
                .title("흠뻑쇼 또 가고싶다...")
                .content("슬슬 뛰어 놀때가 된거같아요ㅜㅜ")
                .commentCount(4)
                .scrapCount(0)
                .inMyScrap(true)
                .isMine(true)
                .isUsedComment(true)
                .createdDate(LocalDateTime.now())
                .imageUrls(List.of("imageUrl1", "imageUrl2"))
                .comments(List.of(comment1, comment2))
                .build();

        given(boardQueryService.searchFreeBoard(anyLong(), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get(BASE_URL + "/free-boards/{boardId}", 2023, 100000, 1)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-free-board",
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
                                fieldWithPath("data.boardId").type(JsonFieldType.NUMBER)
                                        .description("게시글 id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING)
                                        .description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING)
                                        .description("게시글 내용"),
                                fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 댓글수"),
                                fieldWithPath("data.scrapCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 스크랩 수"),
                                fieldWithPath("data.inMyScrap").type(JsonFieldType.BOOLEAN)
                                        .description("스크랩 여부"),
                                fieldWithPath("data.isMine").type(JsonFieldType.BOOLEAN)
                                        .description("게시글 본인 작성 여부"),
                                fieldWithPath("data.isUsedComment").type(JsonFieldType.BOOLEAN)
                                        .description("게시글 댓글 작성 여부"),
                                fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                                        .description("게시글 작성일시"),
                                fieldWithPath("data.imageUrls").type(JsonFieldType.ARRAY)
                                        .description("게시글 이미지"),
                                fieldWithPath("data.comments").type(JsonFieldType.ARRAY)
                                        .description("게시글 댓글"),
                                fieldWithPath("data.comments[].commentId").type(JsonFieldType.NUMBER)
                                        .description("댓글 id"),
                                fieldWithPath("data.comments[].sender").type(JsonFieldType.NUMBER)
                                        .description("댓글 작성자 번호"),
                                fieldWithPath("data.comments[].content").type(JsonFieldType.STRING)
                                        .description("댓글 내용"),
                                fieldWithPath("data.comments[].depth").type(JsonFieldType.NUMBER)
                                        .description("댓글 깊이"),
                                fieldWithPath("data.comments[].isMine").type(JsonFieldType.BOOLEAN)
                                        .description("댓글 본인 작성 여부"),
                                fieldWithPath("data.comments[].createdDate").type(JsonFieldType.ARRAY)
                                        .description("댓글 작성일"),
                                fieldWithPath("data.comments[].reComments").type(JsonFieldType.ARRAY)
                                        .description("대댓글"),
                                fieldWithPath("data.comments[].reComments[].commentId").type(JsonFieldType.NUMBER)
                                        .description("대댓글 id"),
                                fieldWithPath("data.comments[].reComments[].sender").type(JsonFieldType.NUMBER)
                                        .description("대댓글 작성자 번호"),
                                fieldWithPath("data.comments[].reComments[].content").type(JsonFieldType.STRING)
                                        .description("대댓글 내용"),
                                fieldWithPath("data.comments[].reComments[].depth").type(JsonFieldType.NUMBER)
                                        .description("대댓글 깊이"),
                                fieldWithPath("data.comments[].reComments[].isMine").type(JsonFieldType.BOOLEAN)
                                        .description("대댓글 본인 작성 여부"),
                                fieldWithPath("data.comments[].reComments[].createdDate").type(JsonFieldType.ARRAY)
                                        .description("대댓글 작성일")
                        )
                ));
    }

    @DisplayName("공지사항 상세 조회 API")
    @Test
    void searchNoticeBoard() throws Exception {
        given(tokenUtils.getUserKey())
                .willReturn(UUID.randomUUID().toString());

        BoardDetailResponse.ReCommentVo reComment1 = BoardDetailResponse.ReCommentVo.builder()
                .commentId(2L)
                .sender(0)
                .content("일주일 소요 예정입니다.")
                .depth(2)
                .isMine(false)
                .createdDate(LocalDateTime.now())
                .build();

        BoardDetailResponse.CommentVo comment1 = BoardDetailResponse.CommentVo.builder()
                .commentId(1L)
                .sender(1)
                .content("공사는 언제 끝나나요?")
                .depth(1)
                .isMine(true)
                .createdDate(LocalDateTime.now())
                .reComments(List.of(reComment1))
                .build();

        BoardDetailResponse.CommentVo comment2 = BoardDetailResponse.CommentVo.builder()
                .commentId(3L)
                .sender(1)
                .content("알겠습니다.")
                .depth(1)
                .isMine(true)
                .createdDate(LocalDateTime.now())
                .reComments(new ArrayList<>())
                .build();

        BoardDetailResponse response = BoardDetailResponse.builder()
                .boardId(1L)
                .title("학교 시설 보수")
                .content("11월 2일부터 교내 배수로 보수 공사에 들어갑니다.")
                .commentCount(3)
                .scrapCount(0)
                .inMyScrap(false)
                .isMine(false)
                .createdDate(LocalDateTime.now())
                .imageUrls(List.of("imageUrl1", "imageUrl2"))
                .comments(List.of(comment1, comment2))
                .build();

        given(boardQueryService.searchBoard(anyLong(), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get(BASE_URL + "/notice-boards/{boardId}", 2023, 100000, 1)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-notice-board",
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
                                fieldWithPath("data.boardId").type(JsonFieldType.NUMBER)
                                        .description("게시글 id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING)
                                        .description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING)
                                        .description("게시글 내용"),
                                fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 댓글수"),
                                fieldWithPath("data.scrapCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 스크랩 수"),
                                fieldWithPath("data.inMyScrap").type(JsonFieldType.BOOLEAN)
                                        .description("스크랩 여부"),
                                fieldWithPath("data.isMine").type(JsonFieldType.BOOLEAN)
                                        .description("게시글 본인 작성 여부"),
                                fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                                        .description("게시글 작성일시"),
                                fieldWithPath("data.imageUrls").type(JsonFieldType.ARRAY)
                                        .description("게시글 이미지"),
                                fieldWithPath("data.comments").type(JsonFieldType.ARRAY)
                                        .description("게시글 댓글"),
                                fieldWithPath("data.comments[].commentId").type(JsonFieldType.NUMBER)
                                        .description("댓글 id"),
                                fieldWithPath("data.comments[].sender").type(JsonFieldType.NUMBER)
                                        .description("댓글 작성자 번호"),
                                fieldWithPath("data.comments[].content").type(JsonFieldType.STRING)
                                        .description("댓글 내용"),
                                fieldWithPath("data.comments[].depth").type(JsonFieldType.NUMBER)
                                        .description("댓글 깊이"),
                                fieldWithPath("data.comments[].isMine").type(JsonFieldType.BOOLEAN)
                                        .description("댓글 본인 작성 여부"),
                                fieldWithPath("data.comments[].createdDate").type(JsonFieldType.ARRAY)
                                        .description("댓글 작성일"),
                                fieldWithPath("data.comments[].reComments").type(JsonFieldType.ARRAY)
                                        .description("대댓글"),
                                fieldWithPath("data.comments[].reComments[].commentId").type(JsonFieldType.NUMBER)
                                        .description("대댓글 id"),
                                fieldWithPath("data.comments[].reComments[].sender").type(JsonFieldType.NUMBER)
                                        .description("대댓글 작성자 번호"),
                                fieldWithPath("data.comments[].reComments[].content").type(JsonFieldType.STRING)
                                        .description("대댓글 내용"),
                                fieldWithPath("data.comments[].reComments[].depth").type(JsonFieldType.NUMBER)
                                        .description("대댓글 깊이"),
                                fieldWithPath("data.comments[].reComments[].isMine").type(JsonFieldType.BOOLEAN)
                                        .description("대댓글 본인 작성 여부"),
                                fieldWithPath("data.comments[].reComments[].createdDate").type(JsonFieldType.ARRAY)
                                        .description("대댓글 작성일")
                        )
                ));
    }

    @DisplayName("가정통신문 상세 조회 API")
    @Test
    void searchCommunicationBoard() throws Exception {
        given(tokenUtils.getUserKey())
                .willReturn(UUID.randomUUID().toString());

        BoardDetailResponse.ReCommentVo reComment1 = BoardDetailResponse.ReCommentVo.builder()
                .commentId(2L)
                .sender(0)
                .content("알겠습니다.")
                .depth(2)
                .isMine(false)
                .createdDate(LocalDateTime.now())
                .build();

        BoardDetailResponse.CommentVo comment1 = BoardDetailResponse.CommentVo.builder()
                .commentId(1L)
                .sender(1)
                .content("우리 아이 사진 많이 찍어주세요.")
                .depth(1)
                .isMine(false)
                .createdDate(LocalDateTime.now())
                .reComments(List.of(reComment1))
                .build();

        BoardDetailResponse response = BoardDetailResponse.builder()
                .boardId(1L)
                .title("수학 여행")
                .content("제주도로 수학 여행을 떠날 예정입니다.")
                .commentCount(1)
                .scrapCount(0)
                .inMyScrap(true)
                .isMine(false)
                .createdDate(LocalDateTime.now())
                .imageUrls(List.of("imageUrl1", "imageUrl2"))
                .comments(List.of(comment1))
                .build();

        given(boardQueryService.searchBoard(anyLong(), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get(BASE_URL + "/communication-boards/{boardId}", 2023, 100000, 1)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-communication-board",
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
                                fieldWithPath("data.boardId").type(JsonFieldType.NUMBER)
                                        .description("게시글 id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING)
                                        .description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING)
                                        .description("게시글 내용"),
                                fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 댓글수"),
                                fieldWithPath("data.scrapCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 스크랩 수"),
                                fieldWithPath("data.inMyScrap").type(JsonFieldType.BOOLEAN)
                                        .description("내가 스크랩 했나?"),
                                fieldWithPath("data.isMine").type(JsonFieldType.BOOLEAN)
                                        .description("게시글 본인 작성 여부"),
                                fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                                        .description("게시글 작성일시"),
                                fieldWithPath("data.imageUrls").type(JsonFieldType.ARRAY)
                                        .description("게시글 이미지"),
                                fieldWithPath("data.comments").type(JsonFieldType.ARRAY)
                                        .description("게시글 댓글"),
                                fieldWithPath("data.comments[].commentId").type(JsonFieldType.NUMBER)
                                        .description("댓글 id"),
                                fieldWithPath("data.comments[].sender").type(JsonFieldType.NUMBER)
                                        .description("댓글 작성자 번호"),
                                fieldWithPath("data.comments[].content").type(JsonFieldType.STRING)
                                        .description("댓글 내용"),
                                fieldWithPath("data.comments[].depth").type(JsonFieldType.NUMBER)
                                        .description("댓글 깊이"),
                                fieldWithPath("data.comments[].isMine").type(JsonFieldType.BOOLEAN)
                                        .description("댓글 본인 작성 여부"),
                                fieldWithPath("data.comments[].createdDate").type(JsonFieldType.ARRAY)
                                        .description("댓글 작성일"),
                                fieldWithPath("data.comments[].reComments").type(JsonFieldType.ARRAY)
                                        .description("대댓글"),
                                fieldWithPath("data.comments[].reComments[].commentId").type(JsonFieldType.NUMBER)
                                        .description("대댓글 id"),
                                fieldWithPath("data.comments[].reComments[].sender").type(JsonFieldType.NUMBER)
                                        .description("대댓글 작성자 번호"),
                                fieldWithPath("data.comments[].reComments[].content").type(JsonFieldType.STRING)
                                        .description("대댓글 내용"),
                                fieldWithPath("data.comments[].reComments[].depth").type(JsonFieldType.NUMBER)
                                        .description("대댓글 깊이"),
                                fieldWithPath("data.comments[].reComments[].isMine").type(JsonFieldType.BOOLEAN)
                                        .description("대댓글 본인 작성 여부"),
                                fieldWithPath("data.comments[].reComments[].createdDate").type(JsonFieldType.ARRAY)
                                        .description("대댓글 작성일")
                        )
                ));
    }

    private BoardResponse createBoardResponse(Long boardId, String title, String content, int commentCount) {
        return BoardResponse.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .commentCount(commentCount)
                .scrapCount(0)
                .createdDate(LocalDateTime.now())
                .build();
    }

    private NewCommunicationResponse createNewCommunicationResponse(long boardId, String title) {
        return NewCommunicationResponse.builder()
                .boardId(boardId)
                .title(title)
                .createdDate(LocalDateTime.now())
                .build();
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
