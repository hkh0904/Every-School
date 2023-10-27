package com.everyschool.schoolservice.api.controller.school;

import com.everyschool.schoolservice.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SchoolQueryControllerTest extends ControllerTestSupport {

    @DisplayName("학교 목록을 조회시 query 파라미터는 필수값이다.")
    @Test
    void searchSchoolsWithoutQuery() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get("/school-service/v1/schools")
            )
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @DisplayName("학교 목록을 조회한다.")
    @Test
    void searchSchools() throws Exception {
        //given

        //when //then
        mockMvc.perform(
            get("/school-service/v1/schools")
                .param("query", "수완")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }

    @DisplayName("학교 정보를 조회한다.")
    @Test
    void searchSchool() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get("/school-service/v1/schools/{schoolId}", 1L)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }
}