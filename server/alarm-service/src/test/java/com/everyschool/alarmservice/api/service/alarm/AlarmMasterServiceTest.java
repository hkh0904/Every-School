package com.everyschool.alarmservice.api.service.alarm;

import com.everyschool.alarmservice.IntegrationTestSupport;
import com.everyschool.alarmservice.api.client.user.UserServiceClient;
import com.everyschool.alarmservice.api.client.user.resquest.UserIdRequest;
import com.everyschool.alarmservice.api.controller.alarm.response.SendAlarmResponse;
import com.everyschool.alarmservice.api.service.alarm.dto.CreateAlarmDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

class AlarmMasterServiceTest extends IntegrationTestSupport {


    @Autowired
    private AlarmMasterService alarmMasterService;

    @MockBean
    private UserServiceClient userServiceClient;

    //시스템 전송
    //사용자 전송
    @DisplayName("시스템이 알림 정보를 입력 받아 알림을 생성한다.")
    @Test
    void createAlarmForSystem() {
        //given
        List<String> recipientUserKeys = List.of(
            createUUID(),
            createUUID(),
            createUUID(),
            createUUID(),
            createUUID()
        );
        CreateAlarmDto dto = CreateAlarmDto.builder()
            .title("가정통신문")
            .content("가정통신문입니다.")
            .schoolYear(2023)
            .recipientUserKeys(recipientUserKeys)
            .build();

        List<Long> ids = List.of(1L, 2L, 3L, 4L, 5L);

        given(userServiceClient.searchUserIds(any(UserIdRequest.class)))
            .willReturn(ids);

        //when
        SendAlarmResponse response = alarmMasterService.createAlarm(null, dto);

        //then
        assertThat(response.getSuccessSendCount()).isEqualTo(5);
    }

    @DisplayName("보낸 사람 회원키와 알림 정보를 입력 받아 알림을 생성한다.")
    @Test
    void createAlarmForUser() {
        //given
        List<String> recipientUserKeys = List.of(
            createUUID(),
            createUUID(),
            createUUID(),
            createUUID(),
            createUUID()
        );
        CreateAlarmDto dto = CreateAlarmDto.builder()
            .title("가정통신문")
            .content("가정통신문입니다.")
            .schoolYear(2023)
            .recipientUserKeys(recipientUserKeys)
            .build();

        List<Long> ids = List.of(1L, 2L, 3L, 4L, 5L);

        given(userServiceClient.searchUserIds(any(UserIdRequest.class)))
            .willReturn(ids);

        //when
        SendAlarmResponse response = alarmMasterService.createAlarm(createUUID(), dto);

        //then
        assertThat(response.getSuccessSendCount()).isEqualTo(5);
    }

    private String createUUID() {
        return UUID.randomUUID().toString();
    }
}