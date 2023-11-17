package com.everyschool.alarmservice.api.service.alarm;

import com.everyschool.alarmservice.api.client.user.UserServiceClient;
import com.everyschool.alarmservice.api.client.user.response.UserInfo;
import com.everyschool.alarmservice.api.controller.alarm.response.AlarmResponse;
import com.everyschool.alarmservice.domain.alarm.repository.AlarmQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AlarmQueryService {

    private final AlarmQueryRepository alarmQueryRepository;
    private final UserServiceClient userServiceClient;

    public List<AlarmResponse> searchMyAlarms(String token) {
        UserInfo recipient = userServiceClient.searchUserInfo(token);

        List<AlarmResponse> responses = alarmQueryRepository.findByRecipientId(recipient.getUserId());

        responses.forEach(r -> r.setSender(recipient.getUserName()));

        return responses;
    }
}
