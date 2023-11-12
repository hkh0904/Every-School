package com.everyschool.schoolservice.api.web.controller.schoolapply;

import com.everyschool.schoolservice.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SchoolApplyWebQueryControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/school-service/v1/web/{schoolYear}/schools/{schoolId}";

    @DisplayName("승인 대기 중인 신청 목록을 조회한다.")
    @Test
    void searchWaitSchoolApply() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL + "/wait-apply", 2023, 100000)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }

    @DisplayName("승인된 신청 목록을 조회한다.")
    @Test
    void searchApproveSchoolApply() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL + "/approve-apply", 2023, 100000)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }

    @DisplayName("신청 내역을 상세 조회한다.")
    @Test
    void searchSchoolApply() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL + "/apply/{schoolApplyId}", 2023, 100000, 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }

}