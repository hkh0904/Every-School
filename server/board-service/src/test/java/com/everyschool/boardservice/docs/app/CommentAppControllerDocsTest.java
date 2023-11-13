package com.everyschool.boardservice.docs.app;

import com.everyschool.boardservice.api.app.controller.board.CommentAppController;
import com.everyschool.boardservice.api.app.controller.board.request.CreateCommentRequest;
import com.everyschool.boardservice.api.app.controller.board.response.CreateCommentResponse;
import com.everyschool.boardservice.api.app.service.board.CommentAppService;
import com.everyschool.boardservice.docs.RestDocsSupport;
import com.everyschool.boardservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentAppControllerDocsTest extends RestDocsSupport {

    private final CommentAppService commentAppService = mock(CommentAppService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/board-service/v1/app/{schoolYear}/schools/{schoolId}/boards/{boardId}/comments";

    @Override
    protected Object initController() {
        return new CommentAppController(commentAppService, tokenUtils);
    }

    @DisplayName("댓글 작성 API")
    @Test
    void createComment() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        CreateCommentRequest request = CreateCommentRequest.builder()
            .content("이예리 바보")
            .build();

        CreateCommentResponse response = CreateCommentResponse.builder()
            .commentId(2L)
            .parentCommentId(null)
            .content("이예리 바보")
            .depth(0)
            .createdDate(LocalDateTime.now())
            .build();

        given(commentAppService.createComment(anyString(), anyLong(), any(), anyString()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL, 2023, 100000, 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("app-create-comment",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .description("댓글 내용")
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
                    fieldWithPath("data.commentId").type(JsonFieldType.NUMBER)
                        .description("댓글 아이디"),
                    fieldWithPath("data.parentCommentId").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("부모 댓글 아이디"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING)
                        .description("댓글 내용"),
                    fieldWithPath("data.depth").type(JsonFieldType.NUMBER)
                        .description("댓글 깊이"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("댓글 작성일시")
                )
            ));
    }

    @DisplayName("대댓글 작성 API")
    @Test
    void createChildComment() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        CreateCommentRequest request = CreateCommentRequest.builder()
            .content("이예리 바보")
            .build();

        CreateCommentResponse response = CreateCommentResponse.builder()
            .commentId(2L)
            .parentCommentId(1L)
            .content("이예리 바보")
            .depth(1)
            .createdDate(LocalDateTime.now())
            .build();

        given(commentAppService.createComment(anyString(), anyLong(), any(), anyString()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL + "/{commentId}", 2023, 100000, 1, 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("app-create-child-comment",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .description("댓글 내용")
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
                    fieldWithPath("data.commentId").type(JsonFieldType.NUMBER)
                        .description("댓글 아이디"),
                    fieldWithPath("data.parentCommentId").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("부모 댓글 아이디"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING)
                        .description("댓글 내용"),
                    fieldWithPath("data.depth").type(JsonFieldType.NUMBER)
                        .description("댓글 깊이"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("댓글 작성일시")
                )
            ));
    }
}
