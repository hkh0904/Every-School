package com.everyschool.alarmservice.docs.alarm;

import com.everyschool.alarmservice.api.alarm.AlarmController;
import com.everyschool.alarmservice.api.alarm.request.SendAlarmRequest;
import com.everyschool.alarmservice.docs.RestDocsSupport;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AlarmControllerDocsTest extends RestDocsSupport {

    @Override
    protected Object initController() {
        return new AlarmController();
    }

    @DisplayName("사용자 알림 전송 API")
    @Test
    void sendAlarm() throws Exception {
        SendAlarmRequest request = SendAlarmRequest.builder()
            .title("가정통신문")
            .content("가정통신문입니다.")
            .year(2023)
            .userKeys(List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()))
            .build();

        mockMvc.perform(
            post("/alarm-service/alarms")
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
                    fieldWithPath("year").type(JsonFieldType.NUMBER)
                        .optional()
                        .description("학년도"),
                    fieldWithPath("userKeys").type(JsonFieldType.ARRAY)
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
}
