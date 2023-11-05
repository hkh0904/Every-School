package com.everyschool.boardservice.docs.web.board;

import com.everyschool.boardservice.api.controller.FileStore;
import com.everyschool.boardservice.api.controller.board.request.CreateBoardRequest;
import com.everyschool.boardservice.api.controller.board.response.CreateBoardResponse;
import com.everyschool.boardservice.api.web.controller.board.BoardWebController;
import com.everyschool.boardservice.api.web.service.board.BoardWebService;
import com.everyschool.boardservice.docs.RestDocsSupport;
import com.everyschool.boardservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static com.everyschool.boardservice.domain.board.Category.FREE;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoardWebControllerDocsTest extends RestDocsSupport {

    private final BoardWebService boardWebService = mock(BoardWebService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private final FileStore fileStore = mock(FileStore.class);
    private static final String BASE_URL = "/board-service/v1/web/{schoolYear}/schools/{schoolId}/boards";

    @Override
    protected Object initController() {
        return new BoardWebController(boardWebService, tokenUtils, fileStore);
    }

    @DisplayName("공지사항 작성 API")
    @Test
    void createNoticeBoard() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        CreateBoardRequest request = CreateBoardRequest.builder()
            .title("리온이가 너무 귀여워요!")
            .content("애교쟁이에요!")
            .isUsedComment(false)
            .files(new ArrayList<>())
            .build();

        CreateBoardResponse response = CreateBoardResponse.builder()
            .boardId(1L)
            .category(FREE.getText())
            .title("리온이가 너무 귀여워요!")
            .isUsedComment(false)
            .fileCount(0)
            .createdDate(LocalDateTime.now())
            .build();
        given(boardWebService.createNoticeBoard(anyString(), anyLong(), any()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL + "/notices", 2023, 100000)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("web-create-notice-board",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .description("내용"),
                    fieldWithPath("isUsedComment").type(JsonFieldType.BOOLEAN)
                        .description("댓글 사용 여부"),
                    fieldWithPath("files").type(JsonFieldType.ARRAY)
                        .optional()
                        .description("이미지 파일")
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
                    fieldWithPath("data.boardId").type(JsonFieldType.NUMBER)
                        .description("게시물 id"),
                    fieldWithPath("data.category").type(JsonFieldType.STRING)
                        .description("게시물 카테고리"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("게시물 제목"),
                    fieldWithPath("data.isUsedComment").type(JsonFieldType.BOOLEAN)
                        .description("게시물 댓글 작성 여부"),
                    fieldWithPath("data.fileCount").type(JsonFieldType.NUMBER)
                        .description("게시물 파일수"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("게시물 작성일시")
                )
            ));
    }

    @DisplayName("가정통신문 작성 API")
    @Test
    void createCommunicationBoard() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        CreateBoardRequest request = CreateBoardRequest.builder()
            .title("리온이가 너무 귀여워요!")
            .content("애교쟁이에요!")
            .isUsedComment(false)
            .files(new ArrayList<>())
            .build();

        CreateBoardResponse response = CreateBoardResponse.builder()
            .boardId(1L)
            .category(FREE.getText())
            .title("리온이가 너무 귀여워요!")
            .isUsedComment(false)
            .fileCount(0)
            .createdDate(LocalDateTime.now())
            .build();
        given(boardWebService.createCommunicationBoard(anyString(), anyLong(), any()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL + "/communications", 2023, 100000)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("web-create-communication-board",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .description("내용"),
                    fieldWithPath("isUsedComment").type(JsonFieldType.BOOLEAN)
                        .description("댓글 사용 여부"),
                    fieldWithPath("files").type(JsonFieldType.ARRAY)
                        .optional()
                        .description("이미지 파일")
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
                    fieldWithPath("data.boardId").type(JsonFieldType.NUMBER)
                        .description("게시물 id"),
                    fieldWithPath("data.category").type(JsonFieldType.STRING)
                        .description("게시물 카테고리"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("게시물 제목"),
                    fieldWithPath("data.isUsedComment").type(JsonFieldType.BOOLEAN)
                        .description("게시물 댓글 작성 여부"),
                    fieldWithPath("data.fileCount").type(JsonFieldType.NUMBER)
                        .description("게시물 파일수"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("게시물 작성일시")
                )
            ));
    }
}
