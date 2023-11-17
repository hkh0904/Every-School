package com.everyschool.schoolservice.docs.schoolclass;

import com.everyschool.schoolservice.api.controller.schoolclass.SchoolClassController;
import com.everyschool.schoolservice.api.controller.schoolclass.request.CreateSchoolClassRequest;
import com.everyschool.schoolservice.api.controller.schoolclass.response.CreateSchoolClassResponse;
import com.everyschool.schoolservice.api.service.schoolclass.SchoolClassService;
import com.everyschool.schoolservice.api.service.schoolclass.dto.CreateSchoolClassDto;
import com.everyschool.schoolservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SchoolClassControllerDocsTest extends RestDocsSupport {

    private final SchoolClassService schoolClassService = mock(SchoolClassService.class);

    @Override
    protected Object initController() {
        return new SchoolClassController(schoolClassService);
    }

    @DisplayName("학급 등록 API")
    @Test
    void createSchoolClass() throws Exception {
        CreateSchoolClassRequest request = CreateSchoolClassRequest.builder()
            .userKey(UUID.randomUUID().toString())
            .schoolYear(2023)
            .grade(1)
            .classNum(3)
            .build();

        CreateSchoolClassResponse response = CreateSchoolClassResponse.builder()
            .schoolClassId(1L)
            .schoolName("해송고등학교")
            .schoolYear(2023)
            .grade(1)
            .classNum(3)
            .teacherName("이예리")
            .createdDate(LocalDateTime.now())
            .build();

        given(schoolClassService.createSchoolClass(anyLong(), any(CreateSchoolClassDto.class)))
            .willReturn(response);

        mockMvc.perform(
            post("/school-service/v1/schools/{schoolId}/classes", 1L)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-school-class",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userKey").type(JsonFieldType.STRING)
                        .optional()
                        .description("담임 고유키"),
                    fieldWithPath("schoolYear").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("학년도"),
                    fieldWithPath("grade").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("학년"),
                    fieldWithPath("classNum").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("반")
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
                    fieldWithPath("data.schoolClassId").type(JsonFieldType.NUMBER)
                        .description("학급 id"),
                    fieldWithPath("data.schoolName").type(JsonFieldType.STRING)
                        .description("학교 이름"),
                    fieldWithPath("data.schoolYear").type(JsonFieldType.NUMBER)
                        .description("학년도"),
                    fieldWithPath("data.grade").type(JsonFieldType.NUMBER)
                        .description("학년"),
                    fieldWithPath("data.classNum").type(JsonFieldType.NUMBER)
                        .description("반"),
                    fieldWithPath("data.teacherName").type(JsonFieldType.STRING)
                        .description("담임 이름"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.ARRAY)
                        .description("학급 등록일")
                )
            ));
    }
}
