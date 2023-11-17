package com.everyschool.userservice.api.controller.codedetail;

import com.everyschool.userservice.ControllerTestSupport;
import com.everyschool.userservice.api.controller.codedetail.request.CreateCodeDetailRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CodeDetailControllerTest extends ControllerTestSupport {

    @DisplayName("상세 코드 등록 시 코드 이름은 필수값이다.")
    @Test
    void createCodeDetailWithoutGroupName() throws Exception {
        //given
        CreateCodeDetailRequest request = CreateCodeDetailRequest.builder()
            .build();

        //when //then
        mockMvc.perform(
                post("/v1/code-groups/{groupId}/code-details", 1L)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("코드 이름은 필수입니다."));
    }

    @DisplayName("신규 상세 코드를 생성한다.")
    @Test
    void createCodeDetail() throws Exception {
        //given
        CreateCodeDetailRequest request = CreateCodeDetailRequest.builder()
            .codeName("학생")
            .build();

        //when //then
        mockMvc.perform(
                post("/v1/code-groups/{groupId}/code-details", 1L)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value("201"))
            .andExpect(jsonPath("$.status").value("CREATED"))
            .andExpect(jsonPath("$.message").value("CREATED"));
    }

    @DisplayName("상세 코드를 삭제한다.")
    @Test
    void removeCodeDetail() throws Exception {
        //given
        CreateCodeDetailRequest request = CreateCodeDetailRequest.builder()
            .codeName("학생")
            .build();

        //when //then
        mockMvc.perform(
                delete("/v1/code-groups/{groupId}/code-details/{codeId}", 1L, 2L)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }
}