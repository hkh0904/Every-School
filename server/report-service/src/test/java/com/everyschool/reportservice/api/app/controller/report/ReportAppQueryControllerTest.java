package com.everyschool.reportservice.api.app.controller.report;

import com.everyschool.reportservice.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReportAppQueryControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/report-service/v1/app/{schoolYear}/schools/{schoolId}/reports";

    @DisplayName("나의 신고 내역 목록을 조회한다.")
    @Test
    void searchReports() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL, 2023, 100000)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }

    @DisplayName("나의 신고 내역을 상세 조회 한다.")
    @Test
    void searchReport() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL + "/{reportId}", 2023, 100000, 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }
}