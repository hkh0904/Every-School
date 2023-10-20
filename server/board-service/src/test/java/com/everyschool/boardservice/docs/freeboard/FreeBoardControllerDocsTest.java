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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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

    @Override
    protected Object initController() {
        return new FreeBoardController();
    }
}
