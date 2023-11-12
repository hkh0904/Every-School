package com.everyschool.consultservice.docs.app.consultschedule;

import com.everyschool.consultservice.api.app.controller.consultschedule.ConsultScheduleAppQueryController;
import com.everyschool.consultservice.api.app.controller.consultschedule.response.ConsultScheduleResponse;
import com.everyschool.consultservice.api.app.service.consultschedule.ConsultScheduleAppQueryService;
import com.everyschool.consultservice.docs.RestDocsSupport;
import com.everyschool.consultservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultScheduleAppQueryControllerDocsTest extends RestDocsSupport {

    private final ConsultScheduleAppQueryService consultScheduleAppQueryService = mock(ConsultScheduleAppQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/consult-service/v1/app/{schoolYear}/schools/{schoolId}/consult-schedules";

    @Override
    protected Object initController() {
        return new ConsultScheduleAppQueryController(consultScheduleAppQueryService, tokenUtils);
    }

    @DisplayName("상담 스케줄 조회 API")
    @Test
    void searchConsultInfo() throws Exception {
        List<Boolean> schedule = List.of(true, true, false, true, true, false, false, false);

        ConsultScheduleResponse response = ConsultScheduleResponse.builder()
            .consultScheduleId(1L)
            .description("다음주는 교직원 연수로 상담이 불가합니다.")
            .monday(schedule)
            .tuesday(schedule)
            .wednesday(schedule)
            .thursday(schedule)
            .friday(schedule)
            .build();

        given(consultScheduleAppQueryService.searchTeacherConsultSchedule(anyLong()))
            .willReturn(response);

        mockMvc.perform(
                get(BASE_URL + "/{schoolClassId}", 2023, 100000, 1)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("app-search-consult-info",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName("Authorization")
                        .description("Bearer Access Token")
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
                    fieldWithPath("data.consultScheduleId").type(JsonFieldType.NUMBER)
                        .description("상담 스케줄 아이디"),
                    fieldWithPath("data.description").type(JsonFieldType.STRING)
                        .description("상담 설명"),
                    fieldWithPath("data.monday").type(JsonFieldType.ARRAY)
                        .description("월요일 상담 스케줄"),
                    fieldWithPath("data.tuesday").type(JsonFieldType.ARRAY)
                        .description("화요일 상담 스케줄"),
                    fieldWithPath("data.wednesday").type(JsonFieldType.ARRAY)
                        .description("수요일 상담 스케줄"),
                    fieldWithPath("data.thursday").type(JsonFieldType.ARRAY)
                        .description("목요일 상담 스케줄"),
                    fieldWithPath("data.friday").type(JsonFieldType.ARRAY)
                        .description("금요일 상담 스케줄")
                )
            ));
    }
}
