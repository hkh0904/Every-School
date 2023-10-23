package com.everyschool.boardservice.docs.freeboard;

import com.everyschool.boardservice.api.controller.freeboard.FreeBoardController;
import com.everyschool.boardservice.api.controller.freeboard.request.CreateFreeBoardRequest;
import com.everyschool.boardservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreeBoardControllerDocsTest extends RestDocsSupport {

    @DisplayName("자유게시판 글 작성 API")
    @Test
    void createFreeBoard() throws Exception {
        CreateFreeBoardRequest request = CreateFreeBoardRequest.builder()
            .title("급식 맛없다")
            .content("초밥 먹고싶다")
            .categoryId(2L)
            .schoolId(1L)
            .useComment(true)
            .uploadFiles(new ArrayList<>())
            .build();

        mockMvc.perform(
                post("/board-service/free-boards/{userKey}", UUID.randomUUID().toString())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-free-board",
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
                    fieldWithPath("useComment").type(JsonFieldType.BOOLEAN)
                        .optional()
                        .description("댓글 사용 여부"),
                    fieldWithPath("uploadFiles").type(JsonFieldType.ARRAY)
                        .description("이미지나 파일")
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
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("게시글 제목"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING)
                        .description("게시글 내용"),
                    fieldWithPath("data.hit").type(JsonFieldType.NUMBER)
                        .description("조회수"),
                    fieldWithPath("data.categoryId").type(JsonFieldType.NUMBER)
                        .description("카테고리 코드"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("게시글 작성일시"),
                    fieldWithPath("data.uploadFileNum").type(JsonFieldType.NUMBER)
                        .description("이미지 수")
                )
            ));
    }

    @DisplayName("자유게시판 목록 조회 API")
    @Test
    void searchBoards() throws Exception {

        mockMvc.perform(
                get("/board-service/free-boards/{schoolId}/{userKey}", 1L, UUID.randomUUID().toString())
                    .param("limit", "4")
                    .param("categoryId", "1")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-free-board-list",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("limit")
                        .description("출력할 목록 수"),
                    parameterWithName("categoryId")
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
                        .description("교내 공지 PK"),
                    fieldWithPath("data[].title").type(JsonFieldType.STRING)
                        .description("교내 공지 제목"),
                    fieldWithPath("data[].createDate").type(JsonFieldType.STRING)
                        .description("교내 공지 작성일"),
                    fieldWithPath("data[].commentNumber").type(JsonFieldType.NUMBER)
                        .description("댓글 수")
                )
            ));
    }

    @DisplayName("자유 게시판 상세 조회 API")
    @Test
    void searchBoard() throws Exception {

        mockMvc.perform(
                get("/board-service/free-boards/{schoolId}/{userKey}/{boardId}", 1L, UUID.randomUUID().toString(), 2L)
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
                        .description("게시글 PK"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("게시글 제목"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING)
                        .description("게시글 내용"),
                    fieldWithPath("data.userName").type(JsonFieldType.STRING)
                        .description("작성자"),
                    fieldWithPath("data.createDate").type(JsonFieldType.STRING)
                        .description("게시글 작성일"),
                    fieldWithPath("data.uploadFiles").type(JsonFieldType.ARRAY)
                        .description("파일들"),
                    fieldWithPath("data.comments").type(JsonFieldType.ARRAY)
                        .description("댓글들"),
                    fieldWithPath("data.comments[].userNumber").type(JsonFieldType.NUMBER)
                        .description("댓글 유저 번호"),
                    fieldWithPath("data.comments[].content").type(JsonFieldType.STRING)
                        .description("댓글 내용"),
                    fieldWithPath("data.comments[].createdDate").type(JsonFieldType.STRING)
                        .description("댓글 작성 시간"),
                    fieldWithPath("data.comments[].reComment").type(JsonFieldType.ARRAY)
                        .description("대댓글 목록"),
                    fieldWithPath("data.comments[].reComment[].userNumber").type(JsonFieldType.NUMBER)
                        .description("대댓글 유저 번호"),
                    fieldWithPath("data.comments[].reComment[].content").type(JsonFieldType.STRING)
                        .description("대댓글 내용"),
                    fieldWithPath("data.comments[].reComment[].createdDate").type(JsonFieldType.STRING)
                        .description("대댓글 작성 시간"),
                    fieldWithPath("data.comments[].reComment[].reComment").type(JsonFieldType.ARRAY)
                        .description("대대댓글(null or 빈 값)")
                )
            ));
    }

    @Override
    protected Object initController() {
        return new FreeBoardController();
    }
}
