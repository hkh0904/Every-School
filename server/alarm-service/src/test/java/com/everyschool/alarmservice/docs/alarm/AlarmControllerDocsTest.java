package com.everyschool.alarmservice.docs.alarm;

import com.everyschool.alarmservice.api.controller.alarm.AlarmController;
import com.everyschool.alarmservice.api.controller.alarm.request.SendAlarmRequest;
import com.everyschool.alarmservice.api.controller.alarm.response.RemoveAlarmResponse;
import com.everyschool.alarmservice.api.controller.alarm.response.SendAlarmResponse;
import com.everyschool.alarmservice.api.service.alarm.AlarmMasterService;
import com.everyschool.alarmservice.api.service.alarm.dto.CreateAlarmDto;
import com.everyschool.alarmservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AlarmControllerDocsTest extends RestDocsSupport {

    private final AlarmMasterService alarmMasterService = mock(AlarmMasterService.class);

    @Override
    protected Object initController() {
        return new AlarmController(alarmMasterService);
    }

    @DisplayName("사용자 알림 전송 API")
    @Test
    void sendAlarm() throws Exception {
        SendAlarmRequest request = SendAlarmRequest.builder()
            .title("가정통신문")
            .content("가정통신문입니다.")
            .schoolYear(2023)
            .recipientUserKeys(List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()))
            .build();

        SendAlarmResponse response = SendAlarmResponse.builder()
            .alarmId(1L)
            .title("가정통신문")
            .content("가정통신문입니다.")
            .successSendCount(2)
            .sendDate(LocalDateTime.now())
            .build();

        given(alarmMasterService.createAlarm(anyString(), any(CreateAlarmDto.class)))
            .willReturn(response);

        mockMvc.perform(
                post("/alarm-service/v1/alarms")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("send-alarm",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .optional()
                        .description("알림 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .optional()
                        .description("알림 내용"),
                    fieldWithPath("schoolYear").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("학년도"),
                    fieldWithPath("recipientUserKeys").type(JsonFieldType.ARRAY)
                        .optional()
                        .description("전송 사용자")
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
                    fieldWithPath("data.alarmId").type(JsonFieldType.NUMBER)
                        .description("알림 PK"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("알림 제목"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING)
                        .description("알림 내용"),
                    fieldWithPath("data.successSendCount").type(JsonFieldType.NUMBER)
                        .description("전송 성공 사용자 수"),
                    fieldWithPath("data.sendDate").type(JsonFieldType.ARRAY)
                        .description("전송 일시")
                )
            ));
    }

    @DisplayName("마스터 알림 삭제 API")
    @Test
    void removeAlarmMaster() throws Exception {
        RemoveAlarmResponse response = RemoveAlarmResponse.builder()
            .title("가정통신문")
            .content("가정통신문입니다.")
            .schoolYear(2023)
            .removedDate(LocalDateTime.now())
            .build();

        given(alarmMasterService.removeAlarmMaster(anyLong()))
            .willReturn(response);

        mockMvc.perform(
                delete("/alarm-service/v1/alarms/{alarmMasterId}", 1L)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-alarm-master",
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
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("알림 제목"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING)
                        .description("알림 내용"),
                    fieldWithPath("data.schoolYear").type(JsonFieldType.NUMBER)
                        .description("학년도"),
                    fieldWithPath("data.removedDate").type(JsonFieldType.ARRAY)
                        .description("삭제 일시")
                )
            ));
    }
}
