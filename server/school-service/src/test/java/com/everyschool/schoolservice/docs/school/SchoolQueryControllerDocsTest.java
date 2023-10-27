package com.everyschool.schoolservice.docs.school;

import com.everyschool.schoolservice.api.controller.school.SchoolQueryController;
import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.everyschool.schoolservice.api.service.school.SchoolQueryService;
import com.everyschool.schoolservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SchoolQueryControllerDocsTest extends RestDocsSupport {

    private final SchoolQueryService schoolQueryService = mock(SchoolQueryService.class);

    @Override
    protected Object initController() {
        return new SchoolQueryController(schoolQueryService);
    }

    @DisplayName("학교 리스트 조회 API")
    @Test
    void searchSchools() throws Exception {
        SchoolResponse response1 = SchoolResponse.builder()
            .schoolId(1L)
            .name("수완고등학교")
            .address("광주광역시 광산구 장덕로 155")
            .build();

        SchoolResponse response2 = SchoolResponse.builder()
            .schoolId(2L)
            .name("수완중학교")
            .address("광주광역시 광산구 수완로 19")
            .build();

        SchoolResponse response3 = SchoolResponse.builder()
            .schoolId(3L)
            .name("수완초등학교")
            .address("광주광역시 광산구 장덕로 143")
            .build();

        SchoolResponse response4 = SchoolResponse.builder()
            .schoolId(4L)
            .name("수완하나중학교")
            .address("광주광역시 광산구 수완로105번길 47")
            .build();

        List<SchoolResponse> responses = List.of(response1, response2, response3, response4);

        given(schoolQueryService.searchSchools("수완"))
            .willReturn(responses);

        mockMvc.perform(
                get("/school-service/v1/schools")
                    .param("query", "수완")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-list-school",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("query")
                        .optional()
                        .description("조회할 학교명")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터"),
                    fieldWithPath("data[].schoolId").type(JsonFieldType.NUMBER)
                        .description("학교 Id"),
                    fieldWithPath("data[].name").type(JsonFieldType.STRING)
                        .description("학교명"),
                    fieldWithPath("data[].address").type(JsonFieldType.STRING)
                        .description("학교 주소")
                )
            ));
    }
}
