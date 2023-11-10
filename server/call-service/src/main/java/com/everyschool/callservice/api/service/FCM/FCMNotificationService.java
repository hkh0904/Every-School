package com.everyschool.callservice.api.service.FCM;

import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.client.response.UserInfo;
import com.everyschool.callservice.api.service.FCM.dto.CallDeniedDto;
import com.everyschool.callservice.api.service.FCM.dto.OtherUserFcmDto;
import com.everyschool.callservice.domain.usercall.UserCall;
import com.everyschool.callservice.domain.usercall.repository.UserCallRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Slf4j
public class FCMNotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserServiceClient userServiceClient;
    private final UserCallRepository userCallRepository;

    /**
     * 전화 알림 요청
     *
     * @param dto 상대방 정보
     * @return 알림 요청 성공 메시지
     */
    public String sendNotificationByToken(OtherUserFcmDto dto) throws FirebaseMessagingException {
        log.debug("call FCMNotificationService#sendNotificationByToken");
        String fcmToken = userServiceClient.searchUserFcmByUserKey(dto.getOtherUserKey());
        log.debug("fcmToken = {}", fcmToken);

        if (fcmToken == null || fcmToken.isEmpty()) {
            throw new NoSuchElementException("상대방이 로그인 되어 있지 않습니다.");
        }

        Notification notification = Notification.builder()
                .setTitle(dto.getSenderName())
                .setBody("calling...")
                .build();

        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(notification)
                .putData("type", "call")
                .putData("cname", dto.getCname())
                .build();

        return firebaseMessaging.send(message);
    }

    /**
     * 부재중 생성
     *
     * @param dto 상대방 정보
     * @return 부재중 생성 완료 메시지
     */
    public Boolean createUserCallDenied(CallDeniedDto dto, String token) {

        UserInfo sender = getUserWithToken(token);
        UserInfo receiver = getUserWithUserKey(dto.getOtherUserKey());

        createUserCall(sender, receiver, dto, "M");
        return true;
    }

    /**
     * 통화 취소 생성
     *
     * @param dto 상대방 정보
     * @return 부재중 생성 완료 메시지
     */
    public Boolean createUserCallCancel(CallDeniedDto dto, String token) throws FirebaseMessagingException {
        log.debug("call FCMNotificationService#createUserCallCancel");

        String fcmToken = userServiceClient.searchUserFcmByUserKey(dto.getOtherUserKey());

        log.debug("fcmToken = {}", fcmToken);

        if (fcmToken == null || fcmToken.isEmpty()) {
            throw new NoSuchElementException("상대방이 로그인 되어 있지 않습니다.");
        }

        Notification notification = Notification.builder()
                .setTitle(dto.getSenderName())
                .setBody("calling cancel")
                .build();

        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(notification)
                .putData("type", "cancel")
                .build();

        firebaseMessaging.send(message);

        UserInfo sender = getUserWithToken(token);
        UserInfo receiver = getUserWithUserKey(dto.getOtherUserKey());
        createUserCall(sender, receiver, dto, "C");

        return true;
    }

    UserInfo getUserWithToken(String token) {
        return userServiceClient.searchUserInfo(token);
    }
    UserInfo getUserWithUserKey(String userKey) {
        return userServiceClient.searchUserInfoByUserKey(userKey);
    }

    void createUserCall(UserInfo sender, UserInfo receiver, CallDeniedDto dto, String receiveCall) {

        Long teacherId = sender.getUserId();
        Long otherUserId = receiver.getUserId();
        String senderType = "T";

        if (receiver.getUserType() == 'T') {
            teacherId = receiver.getUserId();
            otherUserId = sender.getUserId();
            senderType = "O";
        }

        UserCall userCall = UserCall.builder()
                .otherUserId(otherUserId)
                .teacherId(teacherId)
                .sender(senderType)
                .senderName(sender.getUserName())
                .receiverName(receiver.getUserName())
                .receiveCall(receiveCall)
                .startDateTime(dto.getStartDateTime())
                .endDateTime(dto.getEndDateTime())
                .build();

        userCallRepository.save(userCall);
    }
}
