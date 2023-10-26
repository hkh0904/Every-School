package com.everyschool.alarmservice.api.service.alarm;

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

    public List<AlarmResponse> searchReceivedAlarm(Long recipientId) {
        // TODO: 2023-10-26 임우택 user-service에서 보낸 사람 이름 조회
        List<AlarmResponse> responses = alarmQueryRepository.findByRecipientId(recipientId);

        return responses;
    }
}
