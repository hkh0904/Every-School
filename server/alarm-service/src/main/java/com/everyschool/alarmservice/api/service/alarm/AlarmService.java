package com.everyschool.alarmservice.api.service.alarm;

import com.everyschool.alarmservice.api.controller.alarm.response.RemoveAlarmResponse;
import com.everyschool.alarmservice.domain.alarm.Alarm;
import com.everyschool.alarmservice.domain.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public RemoveAlarmResponse removeAlarm(Long alarmId) {
        Optional<Alarm> findAlarm = alarmRepository.findMasterById(alarmId);
        if (findAlarm.isEmpty()) {
            throw new NoSuchElementException("등록되지 않은 알림입니다.");
        }
        Alarm alarm = findAlarm.get();

        alarm.remove();

        return RemoveAlarmResponse.of(alarm);
    }
}
