package com.everyschool.consultservice.api.controller.consult;

import com.everyschool.consultservice.ControllerTestSupport;
import com.everyschool.consultservice.api.controller.consult.response.ConsultDetailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ConsultQueryControllerTest extends ControllerTestSupport {

    @DisplayName("상담 내역 목록을 조회한다.")
    @Test
    void searchConsults() throws Exception {
        //given

        //when //then
        mockMvc.perform(
            get("/consult-service/v1/schools/{schoolId}/consults", 1L)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"))
            .andExpect(jsonPath("$.data").isArray());
    }

    @DisplayName("상담 내역 상세 조회한다.")
    @Test
    void searchConsult() throws Exception {
        //given
        ConsultDetailResponse response = ConsultDetailResponse.builder()
            .consultId(1L)
            .typeId(2001)
            .progressStatusId(3001)
            .title("1학년 3반 이리온(모) 이예리")
            .message("리온이가 너무 귀여워요!")
            .resultContent(null)
            .rejectedReason(null)
            .consultDate(LocalDateTime.now())
            .build();

        given(consultQueryService.searchConsult(anyLong()))
            .willReturn(response);

        //when //then
        mockMvc.perform(
                get("/consult-service/v1/schools/{schoolId}/consults/{consultId}", 1L, 2L)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"))
            .andExpect(jsonPath("$.data").isNotEmpty());
    }

}