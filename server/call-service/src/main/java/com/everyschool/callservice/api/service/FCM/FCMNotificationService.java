package com.everyschool.callservice.api.service.FCM;

import com.everyschool.callservice.api.controller.FCM.request.FCMNotificationRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FCMNotificationService {
    private final FirebaseMessaging firebaseMessaging;
//    private final UsersRepository usersRepository;

    public String sendNotificationByToken(FCMNotificationRequest request) {
//
//        Optional<Users> user = usersRepository.findById(request.getTargetUserId());
//
//        if (user.isPresent()) {
//            if (user.get().getFirebaseToken() != null) {
//                Notification notification = Notification.builder()
//                        .setTitle(request.getTitle())
//                        .setBody(request.getBody())
//                        // .setImage(requestDto.getImage())
//                        .build();
//
//                Message message = Message.builder()
//                        .setToken(user.get().getFirebaseToken())
//                        .setNotification(notification)
//                        // .putAllData(requestDto.getData())
//                        .build();
//
//                try {
//                    firebaseMessaging.send(message);
//                    return "알림을 성공적으로 전송했습니다. targetUserId=" + requestDto.getTargetUserId();
//                } catch (FirebaseMessagingException e) {
//                    e.printStackTrace();
//                    return "알림 보내기를 실패하였습니다. targetUserId=" + requestDto.getTargetUserId();
//                }
//            } else {
//                return "서버에 저장된 해당 유저의 FirebaseToken이 존재하지 않습니다. targetUserId=" + requestDto.getTargetUserId();
//            }
//
//        } else {
//            return "해당 유저가 존재하지 않습니다. targetUserId=" + requestDto.getTargetUserId();
//        }

        return "test";
    }
}
