package com.everyschool.schoolservice.docs.school;

import com.everyschool.schoolservice.api.controller.school.SchoolController;
import com.everyschool.schoolservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SchoolControllerDocsTest extends RestDocsSupport {
    @Override
    protected Object initController() {
        return new SchoolController();
    }

    @DisplayName("학교 조회 API")
    @Test
    void searchSchools() throws Exception {
        mockMvc.perform(
                get("/school")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-school",
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
}
