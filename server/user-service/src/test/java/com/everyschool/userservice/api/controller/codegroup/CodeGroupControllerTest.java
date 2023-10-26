package com.everyschool.userservice.api.controller.codegroup;

import com.everyschool.userservice.ControllerTestSupport;
import com.everyschool.userservice.api.controller.codegroup.request.CreateCodeGroupRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
class CodeGroupControllerTest extends ControllerTestSupport {

    @DisplayName("코드 그룹 등록 시 그룹 이름은 필수값이다.")
    @Test
    void createCodeGroupWithoutGroupName() throws Exception {
        //given
        CreateCodeGroupRequest request = CreateCodeGroupRequest.builder()
            .build();

        //when //then
        mockMvc.perform(
                post("/v1/code-groups")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("그룹 이름은 필수입니다."));
    }

    @DisplayName("신규 코드 그룹을 생성한다.")
    @Test
    void createCodeGroup() throws Exception {
        //given
        CreateCodeGroupRequest request = CreateCodeGroupRequest.builder()
            .groupName("회원구분")
            .build();

        //when //then
        mockMvc.perform(
                post("/v1/code-groups")
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

    @DisplayName("코드 그룹을 삭제한다.")
    @Test
    void removeCodeGroup() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                delete("/v1/code-groups/{groupId}", 1L)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"));
    }
}