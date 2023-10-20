package com.everyschool.schoolservice.docs.school;

import com.everyschool.schoolservice.api.controller.school.SchoolController;
import com.everyschool.schoolservice.api.controller.school.request.ClassroomRequest;
import com.everyschool.schoolservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SchoolControllerDocsTest extends RestDocsSupport {
    @Override
    protected Object initController() {
        return new SchoolController();
    }

    @DisplayName("학교 리스트 조회 API")
    @Test
    void searchSchools() throws Exception {
        mockMvc.perform(
                get("/school")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-schools",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                            .description("응답 데이터"),
                    fieldWithPath("data[].name").type(JsonFieldType.STRING)
                            .description("학교 이름"),
                    fieldWithPath("data[].address").type(JsonFieldType.STRING)
                            .description("학교 주소"),
                    fieldWithPath("data[].url").type(JsonFieldType.STRING)
                            .description("학교 홈페이지 주소"),
                    fieldWithPath("data[].tel").type(JsonFieldType.STRING)
                            .description("확교 전화번호")
                )
            ));
    }

    @DisplayName("학교 단건 조회 API")
    @Test
    void searchSchool() throws Exception {
        String schoolId = "1";

        mockMvc.perform(
                        get("/school/{schoolId}", schoolId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-one-school",
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
                                fieldWithPath("data.name").type(JsonFieldType.STRING)
                                        .description("학교 이름"),
                                fieldWithPath("data.address").type(JsonFieldType.STRING)
                                        .description("학교 주소"),
                                fieldWithPath("data.url").type(JsonFieldType.STRING)
                                        .description("학교 홈페이지 주소"),
                                fieldWithPath("data.tel").type(JsonFieldType.STRING)
                                        .description("확교 전화번호")
                        )
                ));
    }

    @DisplayName("학급 생성 API")
    @Test
    void createClassroom() throws Exception {
        String schoolId = "1";
        Long teacherId = Long.parseLong("123123123123");

        ClassroomRequest request = ClassroomRequest.builder()
                .teacherId(teacherId)
                .year(2023)
                .grade(1)
                .name("동팔이")
                .build();

        mockMvc.perform(
                post("/school/{schoolId}/classroom", schoolId)
                    .header("Authorization", "Bearer Token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-classroom",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("teacherId").type(JsonFieldType.NUMBER)
                                        .optional()
                                        .description("교사 ID"),
                                fieldWithPath("year").type(JsonFieldType.NUMBER)
                                        .optional()
                                        .description("현재 년도"),
                                fieldWithPath("grade").type(JsonFieldType.NUMBER)
                                        .optional()
                                        .description("학년"),
                                fieldWithPath("name").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("학급 이름")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.teacherName").type(JsonFieldType.STRING)
                                        .description("교사 이름"),
                                fieldWithPath("data.year").type(JsonFieldType.NUMBER)
                                        .description("년도"),
                                fieldWithPath("data.grade").type(JsonFieldType.NUMBER)
                                        .description("학년"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING)
                                        .description("학급 이름")
                        )
                ));
    }
}
