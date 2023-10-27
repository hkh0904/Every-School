package com.everyschool.alarmservice.api.controller.alarm;

import com.everyschool.alarmservice.api.ApiResponse;
import com.everyschool.alarmservice.api.controller.alarm.response.AlarmResponse;
import com.everyschool.alarmservice.api.service.alarm.AlarmQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/alarm-service/v1/alarms")
public class AlarmQueryController {

    private final AlarmQueryService alarmQueryService;

    @GetMapping
    public ApiResponse<List<AlarmResponse>> searchAlarm() {
        log.debug("call AlarmQueryController#searchAlarm");

        List<AlarmResponse> responses = alarmQueryService.searchReceivedAlarm(1L);
        log.debug("responses={}", responses);

        return ApiResponse.ok(responses);
    }
}
