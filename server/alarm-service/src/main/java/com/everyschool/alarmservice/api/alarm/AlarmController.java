package com.everyschool.alarmservice.api.alarm;

import com.everyschool.alarmservice.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/alarm-service/alarms")
public class AlarmController {

    public ApiResponse<?> sendAlarm() {
        return null;
    }

    public ApiResponse<?> searchAlarm() {
        return null;
    }

    public ApiResponse<?> searchAlarmMaster() {
        return null;
    }

    public ApiResponse<?> removeAlarm() {
        return null;
    }

    public ApiResponse<?> readAlarm() {
        return null;
    }
}
