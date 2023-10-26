package com.everyschool.alarmservice.api.controller.alarm;

import com.everyschool.alarmservice.api.ApiResponse;
import com.everyschool.alarmservice.api.controller.alarm.request.SendAlarmRequest;
import com.everyschool.alarmservice.api.controller.alarm.response.RemoveAlarmResponse;
import com.everyschool.alarmservice.api.controller.alarm.response.SendAlarmResponse;
import com.everyschool.alarmservice.api.service.alarm.AlarmMasterService;
import com.everyschool.alarmservice.api.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/alarm-service/v1/alarms")
public class AlarmController {

    private final AlarmService alarmService;
    private final AlarmMasterService alarmMasterService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<SendAlarmResponse> sendAlarm(@Valid @RequestBody SendAlarmRequest request) {
        log.debug("call senderUserKey#sendAlarm");
        log.debug("SendAlarmRequest={}", request);

        // TODO: 2023-10-26 임우택 토큰에서 꺼내기
        String senderUserKey = UUID.randomUUID().toString();

        SendAlarmResponse response = alarmMasterService.createAlarm(senderUserKey, request.toDto());
        log.debug("SendAlarmResponse={}", request);

        return ApiResponse.created(response);
    }

    @DeleteMapping("/{alarmMasterId}/master")
    public ApiResponse<RemoveAlarmResponse> removeAlarmMaster(@PathVariable Long alarmMasterId) {
        log.debug("call senderUserKey#removeAlarmMaster");
        log.debug("alarmMasterId={}", alarmMasterId);

        RemoveAlarmResponse response = alarmMasterService.removeAlarmMaster(alarmMasterId);
        log.debug("RemoveAlarmResponse={}", response);

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{alarmId}")
    public ApiResponse<RemoveAlarmResponse> removeAlarm(@PathVariable Long alarmId) {
        log.debug("call senderUserKey#removeAlarm");
        log.debug("alarmId={}", alarmId);

        RemoveAlarmResponse response = alarmService.removeAlarm(alarmId);
        log.debug("RemoveAlarmResponse={}", response);

        return ApiResponse.ok(response);
    }

    public ApiResponse<?> searchAlarm() {
        return null;
    }

    public ApiResponse<?> searchAlarmMaster() {
        return null;
    }



    public ApiResponse<?> readAlarm() {
        return null;
    }
}
