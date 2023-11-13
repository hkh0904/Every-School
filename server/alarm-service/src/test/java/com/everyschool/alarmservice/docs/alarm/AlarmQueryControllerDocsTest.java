package com.everyschool.alarmservice.docs.alarm;

import com.everyschool.alarmservice.api.controller.alarm.AlarmQueryController;
import com.everyschool.alarmservice.api.controller.alarm.response.AlarmResponse;
import com.everyschool.alarmservice.api.service.alarm.AlarmQueryService;
import com.everyschool.alarmservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AlarmQueryControllerDocsTest extends RestDocsSupport{

    private final AlarmQueryService alarmQueryService = mock(AlarmQueryService.class);
    @Override
    protected Object initController() {
        return new AlarmQueryController(alarmQueryService);
    }

    @DisplayName("내 알림 목록 조회")
    @Test
    void searchMyAlarms() throws Exception {
        AlarmResponse alarm1 = AlarmResponse.builder()
                .alarmId(1L)
                .title("가정통신문")
                .content("좀 읽어라")
                .isRead(false)
                .schoolYear(2023)
                .receivedDate(LocalDateTime.now())
                .build();
        alarm1.setSender("오연주");

        AlarmResponse alarm2 = AlarmResponse.builder()
                .alarmId(2L)
                .title("댓글 달림")
                .content("알림 좀 읽으라고")
                .isRead(false)
                .schoolYear(2023)
                .receivedDate(LocalDateTime.now())
                .build();
        alarm2.setSender("오연주");

        AlarmResponse alarm3 = AlarmResponse.builder()
                .alarmId(3L)
                .title("신고 당했습니다")
                .content("응 너 이제 망함 ㅋ")
                .isRead(false)
                .schoolYear(2023)
                .receivedDate(LocalDateTime.now())
                .build();
        alarm3.setSender("시스템");

        List<AlarmResponse> responses = List.of(alarm1, alarm2, alarm3);

        given(alarmQueryService.searchMyAlarms(anyString()))
                .willReturn(responses);

        mockMvc.perform(
                        get("/alarm-service/v1/alarms")
                                .header("Authorization", "Bearer Access Token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-my-alarms",
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
                                fieldWithPath("data[].sender").type(JsonFieldType.STRING)
                                        .description("보낸 사람"),
                                fieldWithPath("data[].alarmId").type(JsonFieldType.NUMBER)
                                        .description("알림 ID"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING)
                                        .description("제목"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING)
                                        .description("내용"),
                                fieldWithPath("data[].isRead").type(JsonFieldType.BOOLEAN)
                                        .description("읽음 여부"),
                                fieldWithPath("data[].schoolYear").type(JsonFieldType.NUMBER)
                                        .description("학년도"),
                                fieldWithPath("data[].receivedDate").type(JsonFieldType.ARRAY)
                                        .description("알림 받은 날짜")
                        )
                ));

    }
}
