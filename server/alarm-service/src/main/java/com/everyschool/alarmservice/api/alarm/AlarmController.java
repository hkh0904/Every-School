package com.everyschool.alarmservice.api.alarm;

import com.everyschool.alarmservice.api.ApiResponse;
import com.everyschool.alarmservice.api.alarm.request.SendAlarmRequest;
import com.everyschool.alarmservice.api.alarm.response.SendAlarmResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/alarm-service/alarms")
public class AlarmController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<SendAlarmResponse> sendAlarm(@RequestBody SendAlarmRequest request) {
        SendAlarmResponse response = SendAlarmResponse.builder()
            .alarmId(1L)
            .title("가정통신문")
            .content("가정통신문입니다.")
            .successSendCount(2)
            .sendDate(LocalDateTime.now())
            .build();

        return ApiResponse.created(response);
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
