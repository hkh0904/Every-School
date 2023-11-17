package com.everyschool.alarmservice.api.controller.client;

import com.everyschool.alarmservice.api.controller.client.request.SendTeacherAlarmRequest;
import com.everyschool.alarmservice.api.controller.client.response.SendTeacherAlarmResponse;
import com.everyschool.alarmservice.api.service.alarm.AlarmMasterService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/alarm-service/client/v1")
public class AlarmClientController {

    private final AlarmMasterService alarmMasterService;

    @PostMapping("/alert")
    public SendTeacherAlarmResponse sendTeacherAlarm(@RequestBody SendTeacherAlarmRequest request) throws FirebaseMessagingException {
        log.debug("call AlarmClientController#sendTeacherAlarm");
        log.debug("request={}", request);

        SendTeacherAlarmResponse response = alarmMasterService.sendTeacherAlarm(request.toDto(),
                request.getTeacherUserKey(), request.getObjectId());
        log.debug("response={}", response);

        return response;
    }
}
