package com.everyschool.chatservice.api.service.chat.dto;

import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.service.util.RedisUtils;
import com.everyschool.chatservice.domain.chat.Chat;
import com.everyschool.chatservice.domain.chat.repository.ChatRepository;
import com.everyschool.chatservice.domain.chatroomuser.ChatRoomUser;
import com.everyschool.chatservice.domain.chatroomuser.repository.ChatRoomUserQueryRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final RedisUtils redisUtil;

    private final ChatRoomUserQueryRepository chatRoomUserQueryRepository;

    private final UserServiceClient userServiceClient;
    private final FirebaseMessaging firebaseMessaging;

    /**
     * date에 채팅 있던 채팅방 목록 반환하기
     *
     * @param date
     * @return
     */
    public List<Long> searchChatRoomIdByDate(LocalDate date) {
        List<Chat> chats = chatRepository.findByCreatedDateBetween(date.atStartOfDay(), date.plusDays(1).atStartOfDay());
        List<Long> responses = new ArrayList<>();
        for (Chat chat : chats) {
            if (responses.contains(chat.getChatRoomId())) {
                continue;
            }
            responses.add(chat.getChatRoomId());
        }

        return responses;
    }

    // TODO: 2023-11-09 리팩토링 하기 
    public void sendMessageProcessing(SendMessageDto dto) throws FirebaseMessagingException {
        // 채팅방 인원수 확인
        String chatRoomUserCountKey = "CHAR_ROOM_USER_COUNT_" + dto.getChatRoomId();
        String roomUserCount = redisUtil.getString(chatRoomUserCountKey);

        // 채팅방 1명임 > 상대한테 알림 보냄
        if (roomUserCount != null && Integer.parseInt(roomUserCount) == 1) {
            UserInfo senderUser = userServiceClient.searchUserInfoByUserKey(dto.getSenderUserKey());
            // 채팅방 다른 유저 가져오기
            Long opponentUserId = chatRoomUserQueryRepository.findOpponentUserId(dto.getChatRoomId(), senderUser.getUserId())
                    .orElseThrow(() -> new NoSuchElementException("상대 유저 정보를 찾을 수 없습니다."));
            String notificationMessage = dto.getMessage();
            if (notificationMessage.length() > 50) {
                notificationMessage = notificationMessage.substring(0, 50) + "...";
            }
            Notification notification = Notification.builder()
                    .setTitle(senderUser.getUserName())
                    .setBody(notificationMessage)
                    .build();

            String childName = "";

            if (senderUser.getUserType() == 'M' || senderUser.getUserType() == 'F') {
                ChatRoomUser senderChatRoomUser = chatRoomUserQueryRepository.findChatRoomUserByRoomIdAndUserId(dto.getChatRoomId(), senderUser.getUserId())
                        .orElseThrow(() -> new NoSuchElementException("상대 유저를 찾을 수 없습니다."));
                childName = senderChatRoomUser.getChildName();
            }

            String fcmToken = userServiceClient.searchFcmTokenByUserId(opponentUserId);
            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(notification)
                    .putData("type", "chat")
                    .putData("senderUserName", senderUser.getUserName())
                    .putData("senderUserType", String.valueOf(senderUser.getUserType()))
                    .putData("senderUserChildName", childName)
                    .putData("chatRoomId", String.valueOf(dto.getChatRoomId()))
                    .build();
            firebaseMessaging.send(message);
        }
    }
}
