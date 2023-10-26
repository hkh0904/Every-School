package com.everyschool.alarmservice.api.service.alarm;

import com.everyschool.alarmservice.api.client.user.UserServiceClient;
import com.everyschool.alarmservice.api.client.user.resquest.UserIdRequest;
import com.everyschool.alarmservice.api.controller.alarm.response.SendAlarmResponse;
import com.everyschool.alarmservice.api.service.alarm.dto.CreateAlarmDto;
import com.everyschool.alarmservice.domain.alarm.AlarmMaster;
import com.everyschool.alarmservice.domain.alarm.repository.AlarmMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class AlarmMasterService {

    private final AlarmMasterRepository alarmMasterRepository;
    private final UserServiceClient userServiceClient;

    public SendAlarmResponse createAlarm(String senderUserKey, CreateAlarmDto dto) {
        UserIdRequest request = UserIdRequest.builder()
            .userKeys(dto.getRecipientUserKeys())
            .build();

        List<Long> recipientUserIds = userServiceClient.searchUserIds(request);

        // TODO: 2023-10-26 임우택 보낸 사람 PK 수정
        AlarmMaster alarmMaster = AlarmMaster.createAlarmMaster(dto.getTitle(), dto.getContent(), dto.getSchoolYear(), 1L, recipientUserIds);

        AlarmMaster savedAlarmMaster = alarmMasterRepository.save(alarmMaster);

        return SendAlarmResponse.of(savedAlarmMaster);
    }

}
