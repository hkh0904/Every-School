package com.everyschool.boardservice.docs.board;

import com.everyschool.boardservice.api.controller.board.BoardController;
import com.everyschool.boardservice.api.controller.board.request.CreateBoardRequest;
import com.everyschool.boardservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoardControllerDocsTest extends RestDocsSupport {

    @DisplayName("게시글 작성 API")
    @Test
    void createBoard() throws Exception {
        CreateBoardRequest request = CreateBoardRequest.builder()
            .title("개교 기념일 안내")
            .content("학교 쉬어요~")
            .categoryId(1L)
            .schoolId(1L)
            .classId(1L)
            .useComment(false)
            .build();

        mockMvc.perform(
                post("/board-service/boards/{userKey}", UUID.randomUUID().toString())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-board",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .optional()
                        .description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .optional()
                        .description("게시글 내용"),
                    fieldWithPath("categoryId").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("카테고리 코드"),
                    fieldWithPath("schoolId").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("학교 코드"),
                    fieldWithPath("classId").type(JsonFieldType.NUMBER)
                        .description("학급 코드"),
                    fieldWithPath("useComment").type(JsonFieldType.BOOLEAN)
                        .optional()
                        .description("댓글 사용 여부")
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
                    fieldWithPath("data.userName").type(JsonFieldType.STRING)
                        .description("작성자 이름"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("게시글 제목"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING)
                        .description("게시글 내용"),
                    fieldWithPath("data.hit").type(JsonFieldType.NUMBER)
                        .description("조회수"),
                    fieldWithPath("data.categoryName").type(JsonFieldType.STRING)
                        .description("카테고리 이름"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("게시글 작성일시")
                )
            ));
    }

    @DisplayName("교내 공지 목록 조회 API")
    @Test
    void searchBoards() throws Exception {

        mockMvc.perform(
                get("/board-service/boards/{schoolId}/{userKey}", 1L, UUID.randomUUID().toString())
                    .param("limit", "4")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-board-list",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("limit")
                        .description("출력할 목록 수")
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
                        .description("교내 공지 PK"),
                    fieldWithPath("data[].title").type(JsonFieldType.STRING)
                        .description("교내 공지 제목"),
                    fieldWithPath("data[].createDate").type(JsonFieldType.STRING)
                        .description("교내 공지 작성일")
                )
            ));
    }

    @Override
    protected Object initController() {
        return new BoardController();
    }
}
