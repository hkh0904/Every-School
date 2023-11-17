package com.everyschool.consultservice.docs.web.consultschedule;

import com.everyschool.consultservice.api.web.controller.consultschedule.ConsultScheduleWebQueryController;
import com.everyschool.consultservice.api.web.controller.consultschedule.response.ConsultScheduleResponse;
import com.everyschool.consultservice.api.web.service.consultschedule.ConsultScheduleWebQueryService;
import com.everyschool.consultservice.docs.RestDocsSupport;
import com.everyschool.consultservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
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

public class ConsultScheduleWebQueryControllerDocsTest extends RestDocsSupport {

    private final ConsultScheduleWebQueryService consultScheduleWebQueryService = mock(ConsultScheduleWebQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/consult-service/v1/app/{schoolYear}/schools/{schoolId}/consult-schedules";

    @Override
    protected Object initController() {
        return new ConsultScheduleWebQueryController(consultScheduleWebQueryService, tokenUtils);
    }

    @DisplayName("상담 스케줄 조회 API")
    @Test
    void searchMyConsultSchedule() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        List<Boolean> data = List.of(true, true, true, false, false, false, false, false);
        ConsultScheduleResponse response = ConsultScheduleResponse.builder()
            .consultScheduleId(1L)
            .description("오후에는 상담이 불가능 합니다.")
            .monday(data)
            .tuesday(data)
            .wednesday(data)
            .thursday(data)
            .friday(data)
            .lastModifiedDate(LocalDateTime.now())
            .build();

        given(consultScheduleWebQueryService.searchMyConsultSchedule(anyString()))
            .willReturn(response);

        mockMvc.perform(
            get(BASE_URL, 2023, 100000)
                .header("Authorization", "Bearer Access Token")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-my-consult-schedule",
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
                    fieldWithPath("data.consultScheduleId").type(JsonFieldType.NUMBER)
                        .description("상담 스케줄 id"),
                    fieldWithPath("data.description").type(JsonFieldType.STRING)
                        .description("상담 설명"),
                    fieldWithPath("data.monday").type(JsonFieldType.ARRAY)
                        .description("월요일 상담 가능 시간"),
                    fieldWithPath("data.tuesday").type(JsonFieldType.ARRAY)
                        .description("화요일 상담 가능 시간"),
                    fieldWithPath("data.wednesday").type(JsonFieldType.ARRAY)
                        .description("수요일 상담 가능 시간"),
                    fieldWithPath("data.thursday").type(JsonFieldType.ARRAY)
                        .description("목요일 상담 가능 시간"),
                    fieldWithPath("data.friday").type(JsonFieldType.ARRAY)
                        .description("금요일 상담 가능 시간"),
                    fieldWithPath("data.lastModifiedDate").type(JsonFieldType.ARRAY)
                        .description("최종 수정일시")
                )
            ));
    }
}
