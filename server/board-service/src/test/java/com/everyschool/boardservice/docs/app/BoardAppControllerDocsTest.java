package com.everyschool.boardservice.docs.app;

import com.everyschool.boardservice.api.app.controller.board.BoardAppController;
import com.everyschool.boardservice.api.controller.FileStore;
import com.everyschool.boardservice.api.controller.board.request.CreateBoardRequest;
import com.everyschool.boardservice.api.controller.board.response.CreateBoardResponse;
import com.everyschool.boardservice.api.app.service.board.BoardAppService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoardAppControllerDocsTest extends RestDocsSupport {

    private final BoardAppService boardService = mock(BoardAppService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private final FileStore fileStore = mock(FileStore.class);
    private static final String BASE_URL = "/board-service/v1/app/{schoolYear}/schools/{schoolId}";

    @Override
    protected Object initController() {
        return new BoardAppController(boardService, tokenUtils, fileStore);
    }

    @DisplayName("자유 게시판 작성 API")
    @Test
    void createFreeBoard() throws Exception {
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
        given(boardService.createFreeBoard(anyString(), anyLong(), any()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL + "/free-boards", 2023, 100000)
                    .param("title", request.getTitle())
                    .param("content", request.getContent())
                    .param("isUsedComment", String.valueOf(request.getIsUsedComment()))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-free-board",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("title")
                        .description("제목"),
                    parameterWithName("content")
                        .description("내용"),
                    parameterWithName("isUsedComment")
                        .description("댓글 사용 여부"),
                    parameterWithName("files")
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
