package com.everyschool.reportservice.api.web.controller.report;

import com.everyschool.reportservice.ControllerTestSupport;
import com.everyschool.reportservice.api.web.controller.report.request.EditResultRequest;
import com.everyschool.reportservice.api.web.controller.report.request.EditStatusRequest;
import com.everyschool.reportservice.domain.report.ProgressStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.everyschool.reportservice.domain.report.ProgressStatus.*;
import static com.everyschool.reportservice.error.ErrorMessage.UNREGISTERED_PROGRESS_STATUS;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReportWebControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/report-service/v1/web/{schoolYear}/schools/{schoolId}/reports";

    @DisplayName("신고 처리 상태 수정시 처리 상태는 필수값이다.")
    @Test
    void editStatusWithoutStatus() throws Exception {
        //given
        EditStatusRequest request = EditStatusRequest.builder()
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{reportId}", 2023, 100000, 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("처리 상태 코드는 필수입니다."));
    }

    @DisplayName("신고 처리 상태 수정시 처리 상태는 7001 ~ 7003이다.")
    @Test
    void editStatusNotContainStatus() throws Exception {
        //given
        EditStatusRequest request = EditStatusRequest.builder()
            .status(7000)
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{reportId}", 2023, 100000, 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(UNREGISTERED_PROGRESS_STATUS.getMessage()));
    }

    @DisplayName("신고 처리 상태를 수정한다.")
    @Test
    void editStatus() throws Exception {
        //given
        EditStatusRequest request = EditStatusRequest.builder()
            .status(PROCESS.getCode())
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{reportId}", 2023, 100000, 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }

    @DisplayName("신고 처리 결과 수정시 처리 결과는 필수값이다.")
    @Test
    void editResultWithoutResult() throws Exception {
        //given
        EditResultRequest request = EditResultRequest.builder()
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{reportId}/result", 2023, 100000, 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 처리 결과는 필수입니다."));
    }

    @DisplayName("신고 처리 결과 수정시 처리 결과는 최대 500자이다.")
    @Test
    void editResultMaxLengthResult() throws Exception {
        //given
        EditResultRequest request = EditResultRequest.builder()
            .result(getText(501))
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{reportId}/result", 2023, 100000, 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 처리 결과는 최대 500자 입니다."));
    }

    @DisplayName("신고 처리 결과를 수정한다.")
    @Test
    void editResult() throws Exception {
        //given
        EditResultRequest request = EditResultRequest.builder()
            .result(getText(500))
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{reportId}/result", 2023, 100000, 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }

    private String getText(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append("0");
        }
        return String.valueOf(builder);
    }
}