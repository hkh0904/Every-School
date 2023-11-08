package com.everyschool.boardservice.api.app.controller.board;

import com.everyschool.boardservice.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardAppControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/board-service/v1/app/{schoolYear}/schools/{schoolId}";

    @DisplayName("자유게시판에 게시물을 등록할 때 제목은 필수값이다.")
    @Test
    void createFreeBoardWithoutTitle() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/free-boards", 2023, 100000)
                    .param("content", "content")
                    .param("isUsedComment", "false")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("게시물 제목은 필수입니다."));
    }

    @DisplayName("자유게시판에 게시물을 등록할 때 제목의 최대 길이는 100자이다.")
    @Test
    void createFreeBoardMaxLengthTitle() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/free-boards", 2023, 100000)
                    .param("title", createText(101))
                    .param("content", "content")
                    .param("isUsedComment", "false")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("게시물 제목의 길이는 최대 100자 입니다."));
    }

    @DisplayName("자유게시판에 게시물을 등록할 때 내용은 필수값이다.")
    @Test
    void createFreeBoardWithoutContent() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/free-boards", 2023, 100000)
                    .param("title", "title")
                    .param("isUsedComment", "false")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("게시물 내용은 필수입니다."));
    }

    @DisplayName("자유게시판에 게시물을 등록한다.")
    @Test
    void createFreeBoard() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/free-boards", 2023, 100000)
                    .param("title", "title")
                    .param("content", "content")
                    .param("isUsedComment", "false")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value("201"))
            .andExpect(jsonPath("$.status").value("CREATED"))
            .andExpect(jsonPath("$.message").value("CREATED"));
    }

    private String createText(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append("0");
        }
        return String.valueOf(builder);
    }
}