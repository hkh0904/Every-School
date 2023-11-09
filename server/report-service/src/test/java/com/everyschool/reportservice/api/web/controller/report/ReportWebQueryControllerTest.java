package com.everyschool.reportservice.api.web.controller.report;

import com.everyschool.reportservice.ControllerTestSupport;
import com.everyschool.reportservice.domain.report.ProgressStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.everyschool.reportservice.error.ErrorMessage.UNREGISTERED_PROGRESS_STATUS;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReportWebQueryControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/report-service/v1/web/{schoolYear}/schools/{schoolId}/reports";

    @DisplayName("신고 목록을 조회시 처리 상태는 필수이다.")
    @Test
    void searchReportsWithoutParam() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL, 2023, 100000)
            )
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @DisplayName("신고 목록을 조회시 처리 상태는 7001 ~ 7003이다.")
    @Test
    void searchReportsNotContainStatus() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL, 2023, 100000)
                    .param("status", "7000")
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(UNREGISTERED_PROGRESS_STATUS.getMessage()));
    }

    @DisplayName("신고 목록을 조회한다.")
    @Test
    void searchReports() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL, 2023, 100000)
                    .param("status", String.valueOf(ProgressStatus.REGISTER.getCode()))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }

    @DisplayName("신고을 상세 조회한다.")
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