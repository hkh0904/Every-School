package com.everyschool.schoolservice.api.web.controller.schoolapply;

import com.everyschool.schoolservice.ControllerTestSupport;
import com.everyschool.schoolservice.api.web.controller.schoolapply.request.RejectSchoolApplyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SchoolApplyWebControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/school-service/v1/web/{schoolYear}/schools/{schoolId}/apply";

    @DisplayName("학교 소속 신청을 승인한다.")
    @Test
    void approveSchoolApply() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{schoolApplyId}/approve", 2023, 100000, 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));

    }

    @DisplayName("학교 소속 신청시 거절 사유는 필수값이다.")
    @Test
    void rejectSchoolApplyWithoutRejectedReason() throws Exception {
        //given
        RejectSchoolApplyRequest request = RejectSchoolApplyRequest.builder()
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{schoolApplyId}/reject", 2023, 100000, 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("거절 사유는 필수입니다."));
    }

    @DisplayName("학교 소속 신청시 거절 사유는 최대 50자이다.")
    @Test
    void rejectSchoolApplyMaxLengthRejectedReason() throws Exception {
        //given
        RejectSchoolApplyRequest request = RejectSchoolApplyRequest.builder()
            .rejectedReason(getText(51))
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{schoolApplyId}/reject", 2023, 100000, 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("거절 사유는 최대 50자 입니다."));
    }

    @DisplayName("학교 소속 신청을 거절한다.")
    @Test
    void rejectSchoolApply() throws Exception {
        //given
        RejectSchoolApplyRequest request = RejectSchoolApplyRequest.builder()
            .rejectedReason("rejectedReason")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{schoolApplyId}/reject", 2023, 100000, 1)
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
        return "0".repeat(Math.max(0, size));
    }

}