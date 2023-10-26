package com.everyschool.alarmservice.api.service.alarm;

import com.everyschool.alarmservice.IntegrationTestSupport;
import com.everyschool.alarmservice.api.client.user.UserServiceClient;
import com.everyschool.alarmservice.api.client.user.resquest.UserIdRequest;
import com.everyschool.alarmservice.api.controller.alarm.response.RemoveAlarmResponse;
import com.everyschool.alarmservice.api.controller.alarm.response.SendAlarmResponse;
import com.everyschool.alarmservice.api.service.alarm.dto.CreateAlarmDto;
import com.everyschool.alarmservice.domain.alarm.AlarmMaster;
import com.everyschool.alarmservice.domain.alarm.repository.AlarmMasterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

class AlarmMasterServiceTest extends IntegrationTestSupport {


    @Autowired
    private AlarmMasterService alarmMasterService;

    @Autowired
    private AlarmMasterRepository alarmMasterRepository;

    @MockBean
    private UserServiceClient userServiceClient;

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

    @DisplayName("존재하지 않는 알림을 삭제하려는 경우 예외가 발생한다.")
    @Test
    void removeAlarmMasterWithoutAlarm() {
        //given

        //when //then
        assertThatThrownBy(() -> alarmMasterService.removeAlarmMaster(1L))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("등록되지 않은 알림입니다.");
    }

    @DisplayName("알림 마스터를 삭제할 수 있다.")
    @Test
    void removeAlarmMaster() {
        //given
        AlarmMaster alarmMaster = saveAlarmMaster();

        //when
        RemoveAlarmResponse response = alarmMasterService.removeAlarmMaster(alarmMaster.getId());

        //then
        assertThat(response.getTitle()).isEqualTo("가정통신문");
    }

    private String createUUID() {
        return UUID.randomUUID().toString();
    }

    private AlarmMaster saveAlarmMaster() {
        AlarmMaster alarmMaster = AlarmMaster.builder()
            .title("가정통신문")
            .content("가정통신문입니다.")
            .schoolYear(2023)
            .senderId(1L)
            .alarms(new ArrayList<>())
            .build();
        return alarmMasterRepository.save(alarmMaster);
    }
}