package com.everyschool.userservice.api.app.controller.user;

import com.everyschool.userservice.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserAppQueryControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/v1/app/{schoolYear}/schools/{schoolId}";

    @DisplayName("선생님 연락처 정보를 조회한다.")
    @Test
    void searchUserContactInfo() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL + "/students", 2023, 100000)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }

    @DisplayName("학급에 소속된 모든 회원의 연락처 정보를 조회한다.")
    @Test
    void searchUserContactInfos() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL + "/teachers", 2023, 100000)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }
}