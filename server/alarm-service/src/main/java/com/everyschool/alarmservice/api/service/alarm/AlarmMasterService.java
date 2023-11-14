package com.everyschool.alarmservice.api.service.alarm;

import com.everyschool.alarmservice.api.client.user.UserServiceClient;
import com.everyschool.alarmservice.api.client.user.response.UserInfo;
import com.everyschool.alarmservice.api.client.user.resquest.UserIdRequest;
import com.everyschool.alarmservice.api.controller.alarm.response.AlarmResponse;
import com.everyschool.alarmservice.api.controller.alarm.response.RemoveAlarmResponse;
import com.everyschool.alarmservice.api.controller.alarm.response.SendAlarmResponse;
import com.everyschool.alarmservice.api.service.alarm.dto.CreateAlarmDto;
import com.everyschool.alarmservice.api.service.fcm.FCMNotificationService;
import com.everyschool.alarmservice.domain.alarm.Alarm;
import com.everyschool.alarmservice.domain.alarm.AlarmMaster;
import com.everyschool.alarmservice.domain.alarm.repository.AlarmMasterRepository;
import com.everyschool.alarmservice.domain.alarm.repository.AlarmQueryRepository;
import com.everyschool.alarmservice.domain.alarm.repository.AlarmRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class AlarmMasterService {

    private final AlarmMasterRepository alarmMasterRepository;
    private final AlarmRepository alarmRepository;
    private final AlarmQueryRepository alarmQueryRepository;
    private final UserServiceClient userServiceClient;
    private final FCMNotificationService fcmNotificationService;

    public SendAlarmResponse createAlarm(String token, CreateAlarmDto dto) throws FirebaseMessagingException {
        log.debug("Alarm AlarmMasterService#createAlarm");
        UserInfo sender = userServiceClient.searchUserInfo(token);
        log.debug("sender = {}", sender);

        Map<Long, String> recipientsInfo = new HashMap<>();
        for (String userKeys : dto.getRecipientUserKeys()) {
            Long userId = userServiceClient.searchUserInfoByUserKey(userKeys).getUserId();
            String fcmToken = userServiceClient.searchUserFcmByUserKey(userKeys);

            recipientsInfo.put(userId, fcmToken);
        }
        log.debug("recipientsInfo = {}", recipientsInfo);

        String result = fcmNotificationService.sendNotification(new ArrayList<>(recipientsInfo.values()), dto.getTitle(),
                dto.getContent(), dto.getType(), sender.getUserName());
        log.debug("fcm alarm result = {}", result);


        AlarmMaster alarmMaster = AlarmMaster.createAlarmMaster(dto.getTitle(), dto.getContent(), dto.getType(),
                dto.getSchoolYear(), sender.getUserId(), recipientsInfo);

        AlarmMaster savedAlarmMaster = alarmMasterRepository.save(alarmMaster);

        return SendAlarmResponse.of(savedAlarmMaster);
    }

    public AlarmResponse updateIsRead(Long alarmId) {
        log.debug("Alarm AlarmMasterService#updateIsRead");
        Optional<Alarm> alarm = alarmRepository.findById(alarmId);

        if (alarm.isEmpty()) {
            throw new NoSuchElementException("해당 알림은 존재 하지 않습니다.");
        }

        log.debug("before alarm = {}", alarm.get());
        alarm.get().updateIsRead();
        log.debug("after alarm = {}", alarm.get());

        Alarm result = alarmRepository.save(alarm.get());

        return alarmQueryRepository.findByAlarmId(alarmId);
    }

    public RemoveAlarmResponse removeAlarmMaster(Long alarmMasterId) {
        Optional<AlarmMaster> findAlarmMaster = alarmMasterRepository.findById(alarmMasterId);
        if (findAlarmMaster.isEmpty()) {
            throw new NoSuchElementException("등록되지 않은 알림입니다.");
        }
        AlarmMaster alarmMaster = findAlarmMaster.get();

        alarmMaster.remove();

        return RemoveAlarmResponse.of(alarmMaster);
    }


}
