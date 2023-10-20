package com.everyschool.schoolservice.docs.school;

import com.everyschool.schoolservice.api.controller.school.SchoolController;
import com.everyschool.schoolservice.api.controller.school.request.ClassroomRequest;
import com.everyschool.schoolservice.api.controller.school.request.EnrollRequest;
import com.everyschool.schoolservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.UUID;

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

    @DisplayName("내 학교 등록 하기 API")
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
        .andExpect(status().isCreated())
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
                fieldWithPath("data").type(JsonFieldType.STRING)
                    .description("응답 데이터")
            )
        ));
    }

    @DisplayName("내 학교 등록 현황 조회 API")
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

    @DisplayName("내 학급 조회 API")
    @Test
    void searchMyClassroom() throws Exception {
        String userKey = UUID.randomUUID().toString();

        mockMvc.perform(
            get("/classroom/{userKey}", userKey)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("search-my-classroom",
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

    @DisplayName("(교사용) 등록 요청 리스트 조회 API")
    @Test
        void searchEnrolls() throws Exception {
        Long schoolClassId = Long.parseLong("123123123123");

        mockMvc.perform(
            get("/enroll/teacher/{schoolClassId}", schoolClassId)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("search-enroll-list",
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
                fieldWithPath("data[].type").type(JsonFieldType.STRING)
                    .description("요청 주체(P: 학부모 신청, C: 학생 신청)"),
                fieldWithPath("data[].childName").type(JsonFieldType.STRING)
                    .description("학생 이름"),
                fieldWithPath("data[].grade").type(JsonFieldType.NUMBER)
                    .description("학년"),
                fieldWithPath("data[].classNum").type(JsonFieldType.NUMBER)
                    .description("반"),
                fieldWithPath("data[].studentNum").type(JsonFieldType.NUMBER)
                    .description("학생 번호"),
                fieldWithPath("data[].appliedDate").type(JsonFieldType.ARRAY)
                    .description("신청 일자")
            )
        ));
    }
}
