package com.everyschool.schoolservice.api.web.controller.schooluser;

import com.everyschool.schoolservice.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SchoolUserWebQueryControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/school-service/v1/web/{schoolYear}/schools/{schoolId}/classes/{schoolClassId}";

    @DisplayName("반 학생 정보를 조회한다.")
    @Test
    void searchMyClassStudents() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL + "/students", 2023, 100000, 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }

    @DisplayName("반 학부모 정보를 조회한다.")
    @Test
    void searchMyClassParents() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL + "/parents", 2023, 100000, 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }

}