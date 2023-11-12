package com.everyschool.consultservice.docs.web.consultschedule;

import com.everyschool.consultservice.api.web.controller.consultschedule.ConsultScheduleWebController;
import com.everyschool.consultservice.api.web.controller.consultschedule.request.EditDescriptionRequest;
import com.everyschool.consultservice.api.web.controller.consultschedule.request.EditScheduleRequest;
import com.everyschool.consultservice.api.web.controller.consultschedule.response.ConsultScheduleResponse;
import com.everyschool.consultservice.api.web.service.consultschedule.ConsultScheduleWebService;
import com.everyschool.consultservice.api.web.service.consultschedule.dto.EditScheduleDto;
import com.everyschool.consultservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultScheduleWebControllerDocsTest extends RestDocsSupport {

    private final ConsultScheduleWebService consultScheduleWebService = mock(ConsultScheduleWebService.class);
    private static final String BASE_URL = "/consult-service/v1/app/{schoolYear}/schools/{schoolId}/consult-schedules";

    @Override
    protected Object initController() {
        return new ConsultScheduleWebController(consultScheduleWebService);
    }

    @DisplayName("상담 스케줄 설명 수정 API")
    @Test
    void editDescription() throws Exception {
        EditDescriptionRequest request = EditDescriptionRequest.builder()
            .description("오후에는 상담이 불가능 합니다.")
            .build();

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

        given(consultScheduleWebService.editDescription(anyLong(), anyString()))
            .willReturn(response);

        mockMvc.perform(
                patch(BASE_URL + "/{consultScheduleId}/description", 2023, 100000, 1)
                    .header("Authorization", "Bearer Access Token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("edit-description",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("description").type(JsonFieldType.STRING)
                        .description("상담 스케줄 설명")
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

    @DisplayName("상담 스케줄 일정 수정 API")
    @Test
    void editSchedules() throws Exception {
        List<Boolean> data = List.of(true, true, true, false, false, false, false, false);

        EditScheduleRequest request = EditScheduleRequest.builder()
            .monday(data)
            .tuesday(data)
            .wednesday(data)
            .thursday(data)
            .friday(data)
            .build();

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

        given(consultScheduleWebService.editSchedule(anyLong(), any(EditScheduleDto.class)))
            .willReturn(response);

        mockMvc.perform(
                patch(BASE_URL + "/{consultScheduleId}/schedules", 2023, 100000, 1)
                    .header("Authorization", "Bearer Access Token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("edit-schedules",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("monday").type(JsonFieldType.ARRAY)
                        .description("월요일 상담 가능 시간"),
                    fieldWithPath("tuesday").type(JsonFieldType.ARRAY)
                        .description("화요일 상담 가능 시간"),
                    fieldWithPath("wednesday").type(JsonFieldType.ARRAY)
                        .description("수요일 상담 가능 시간"),
                    fieldWithPath("thursday").type(JsonFieldType.ARRAY)
                        .description("목요일 상담 가능 시간"),
                    fieldWithPath("friday").type(JsonFieldType.ARRAY)
                        .description("금요일 상담 가능 시간")
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
