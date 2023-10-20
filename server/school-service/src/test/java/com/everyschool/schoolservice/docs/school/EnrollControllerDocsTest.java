package com.everyschool.schoolservice.docs.school;

import com.everyschool.schoolservice.api.controller.enroll.EnrollController;
import com.everyschool.schoolservice.api.controller.enroll.request.EnrollRequest;
import com.everyschool.schoolservice.api.controller.school.request.ClassroomRequest;
import com.everyschool.schoolservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EnrollControllerDocsTest extends RestDocsSupport {
    @Override
    protected Object initController() {
        return new EnrollController();
    }

    @DisplayName("학교 등록 하기 API")
    @Test
    void createEnroll() throws Exception {
        EnrollRequest request = EnrollRequest.builder()
                .schoolName("싸피초등학교")
                .name("이지혁")
                .grade(1)
                .classNum(6)
                .year(2023)
                .type('S')
                .build();

        mockMvc.perform(
            post("/enroll")
                    .header("Authorization", "Bearer Token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("enroll-class",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("schoolName").type(JsonFieldType.STRING)
                        .optional()
                        .description("학교 이름"),
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .optional()
                        .description("이름"),
                    fieldWithPath("grade").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("학년"),
                    fieldWithPath("classNum").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("반"),
                    fieldWithPath("year").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("학년도"),
                    fieldWithPath("type").type(JsonFieldType.STRING)
                        .optional()
                        .description("학부모(P), 자녀(S)")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터")
                )
            ));
    }

    @DisplayName("학교 등록 조회 API")
    @Test
    void searchMyEnroll() throws Exception {
        String userKey = UUID.randomUUID().toString();

        mockMvc.perform(
            get("/enroll/{userKey}", userKey)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("search-my-enroll",
            preprocessResponse(prettyPrint()),
            responseFields(
                fieldWithPath("code").type(JsonFieldType.NUMBER)
                    .description("코드"),
                fieldWithPath("status").type(JsonFieldType.STRING)
                    .description("상태"),
                fieldWithPath("message").type(JsonFieldType.STRING)
                    .description("메시지"),
                fieldWithPath("data").type(JsonFieldType.OBJECT)
                    .description("응답 데이터"),
                fieldWithPath("data.year").type(JsonFieldType.NUMBER)
                    .description("학년도"),
                fieldWithPath("data.schoolName").type(JsonFieldType.STRING)
                    .description("학교 이름"),
                fieldWithPath("data.grade").type(JsonFieldType.NUMBER)
                    .description("학년"),
                fieldWithPath("data.classNum").type(JsonFieldType.NUMBER)
                    .description("반"),
                fieldWithPath("data.name").type(JsonFieldType.STRING)
                    .description("이름"),
                fieldWithPath("data.isApproved").type(JsonFieldType.BOOLEAN)
                    .description("승인 여부"),
                fieldWithPath("data.rejectedReason").type(JsonFieldType.STRING)
                    .description("거절 사유")
            )
        ));
    }

}
