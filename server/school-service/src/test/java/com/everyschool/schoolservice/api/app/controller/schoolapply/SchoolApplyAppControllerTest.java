package com.everyschool.schoolservice.api.app.controller.schoolapply;

import com.everyschool.schoolservice.ControllerTestSupport;
import com.everyschool.schoolservice.api.app.controller.schoolapply.request.CreateSchoolApplyRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SchoolApplyAppControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/school-service/v1/app/{schoolYear}/schools/{schoolId}/apply";

    @DisplayName("학교 소속 신청시 학년 정보는 필수값이다.")
    @Test
    void createSchoolApplyWithoutGrade() throws Exception {
        //given
        CreateSchoolApplyRequest request = CreateSchoolApplyRequest.builder()
            .classNum(2)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("학년 정보는 필수입니다."));
    }

    @DisplayName("학교 소속 신청시 학년 정보는 양수여야 한다.")
    @Test
    void createSchoolApplyPositiveGrade() throws Exception {
        //given
        CreateSchoolApplyRequest request = CreateSchoolApplyRequest.builder()
            .grade(0)
            .classNum(2)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("학년 정보는 양수여야 합니다."));
    }

    @DisplayName("학교 소속 신청시 반 정보는 필수값이다.")
    @Test
    void createSchoolApplyWithoutClassNum() throws Exception {
        //given
        CreateSchoolApplyRequest request = CreateSchoolApplyRequest.builder()
            .grade(1)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("반 정보는 필수입니다."));
    }

    @DisplayName("학교 소속 신청시 반 정보는 양수여야 한다.")
    @Test
    void createSchoolApplyPositiveClassNum() throws Exception {
        //given
        CreateSchoolApplyRequest request = CreateSchoolApplyRequest.builder()
            .grade(1)
            .classNum(0)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("반 정보는 양수여야 합니다."));
    }

    @DisplayName("학교 소속 신청시 반 정보는 양수여야 한다.")
    @Test
    void createSchoolApply() throws Exception {
        //given
        CreateSchoolApplyRequest request = CreateSchoolApplyRequest.builder()
            .grade(1)
            .classNum(3)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value("201"))
            .andExpect(jsonPath("$.status").value("CREATED"))
            .andExpect(jsonPath("$.message").value("CREATED"));
    }
}