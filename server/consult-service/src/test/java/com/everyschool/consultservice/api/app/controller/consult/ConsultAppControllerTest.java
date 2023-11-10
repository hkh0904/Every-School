package com.everyschool.consultservice.api.app.controller.consult;

import com.everyschool.consultservice.ControllerTestSupport;
import com.everyschool.consultservice.api.app.controller.consult.request.CreateConsultRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.everyschool.consultservice.domain.consult.ConsultType.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ConsultAppControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/consult-service/v1/app/{schoolYear}/schools/{schoolId}/consults";

    @DisplayName("상담 등록 시 교직원 고유키는 필수값이다.")
    @Test
    void createConsultWithoutTeacherKey() throws Exception {
        //given
        CreateConsultRequest request = CreateConsultRequest.builder()
            .teacherKey(" ")
            .studentKey(createUUID())
            .typeId(VISIT.getCode())
            .consultDateTime(LocalDateTime.now())
            .message("message")
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
            .andExpect(jsonPath("$.message").value("교직원 고유키는 필수입니다."));
    }

    @DisplayName("상담 등록 시 자녀 고유키는 필수값이다.")
    @Test
    void createConsultWithoutStudentKey() throws Exception {
        //given
        CreateConsultRequest request = CreateConsultRequest.builder()
            .teacherKey(createUUID())
            .studentKey(" ")
            .typeId(VISIT.getCode())
            .consultDateTime(LocalDateTime.now())
            .message("message")
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
            .andExpect(jsonPath("$.message").value("자녀 고유키는 필수입니다."));
    }

    @DisplayName("상담 등록 시 상담 종류는 필수값이다.")
    @Test
    void createConsultWithoutTypeId() throws Exception {
        //given
        CreateConsultRequest request = CreateConsultRequest.builder()
            .teacherKey(createUUID())
            .studentKey(createUUID())
            .typeId(null)
            .consultDateTime(LocalDateTime.now())
            .message("message")
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
            .andExpect(jsonPath("$.message").value("상담 종류는 필수입니다."));
    }

    @DisplayName("상담 등록 시 상담 시간는 필수값이다.")
    @Test
    void createConsultWithoutConsultDateTime() throws Exception {
        //given
        CreateConsultRequest request = CreateConsultRequest.builder()
            .teacherKey(createUUID())
            .studentKey(createUUID())
            .typeId(VISIT.getCode())
            .consultDateTime(null)
            .message("message")
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
            .andExpect(jsonPath("$.message").value("상담 시간은 필수입니다."));
    }

    @DisplayName("상담 등록 시 상담 사유는 필수값이다.")
    @Test
    void createConsultWithoutMessage() throws Exception {
        //given
        CreateConsultRequest request = CreateConsultRequest.builder()
            .teacherKey(createUUID())
            .studentKey(createUUID())
            .typeId(VISIT.getCode())
            .consultDateTime(LocalDateTime.now())
            .message(" ")
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
            .andExpect(jsonPath("$.message").value("상담 사유는 필수입니다."));
    }

    @DisplayName("상담 등록 시 상담 사유는 최대 500자이다.")
    @Test
    void createConsultMaxLengthMessage() throws Exception {
        //given
        CreateConsultRequest request = CreateConsultRequest.builder()
            .teacherKey(createUUID())
            .studentKey(createUUID())
            .typeId(VISIT.getCode())
            .consultDateTime(LocalDateTime.now())
            .message(getText(501))
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
            .andExpect(jsonPath("$.message").value("상담 사유는 최대 500자입니다."));
    }

    @DisplayName("상담 등록을 한다.")
    @Test
    void createConsult() throws Exception {
        //given
        CreateConsultRequest request = CreateConsultRequest.builder()
            .teacherKey(createUUID())
            .studentKey(createUUID())
            .typeId(VISIT.getCode())
            .consultDateTime(LocalDateTime.now())
            .message(getText(500))
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

    private String createUUID() {
        return UUID.randomUUID().toString();
    }

    private String getText(int size) {
        return "0".repeat(Math.max(0, size));
    }
}