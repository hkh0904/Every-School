package com.everyschool.chatservice.api.service.chat.dto;

import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.controller.client.response.CheckingChatResponse;
import com.everyschool.chatservice.api.service.util.RedisUtils;
import com.everyschool.chatservice.domain.chat.Chat;
import com.everyschool.chatservice.domain.chat.ChatStatus;
import com.everyschool.chatservice.domain.chat.repository.ChatRepository;
import com.everyschool.chatservice.domain.chatroomuser.ChatRoomUser;
import com.everyschool.chatservice.domain.chatroomuser.repository.ChatRoomUserQueryRepository;
import com.everyschool.chatservice.domain.chatroomuser.repository.ChatRoomUserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRepository chatRepository;

    private final RedisUtils redisUtil;

    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatRoomUserQueryRepository chatRoomUserQueryRepository;

    private final UserServiceClient userServiceClient;
    private final FirebaseMessaging firebaseMessaging;

    /**
     * AI 확인용 채팅 리스트 반환
     *
     * @param chatRoomId
     * @param date
     * @return
     */
    public CheckingChatResponse searchChatListForContentCheck(Long chatRoomId, LocalDate date) {
        List<Chat> chatList = chatRepository.findChatsByChatRoomIdAndCreatedDateBetweenAndStatus(chatRoomId, date.atStartOfDay(), date.plusDays(1).atStartOfDay(), ChatStatus.PLANE.getCode());
        List<ChatRoomUser> users = chatRoomUserQueryRepository.findChatRoomUsersByChatRoomId(chatRoomId);
        ChatRoomUser teacherUser = users.get(0);
        ChatRoomUser otherUser = users.get(1);

        if (users.get(0).getOpponentUserType().equals("T")) {
            teacherUser = users.get(1);
            otherUser = users.get(0);
        }

        return CheckingChatResponse.builder()
                .teacherId(teacherUser.getUserId())
                .teacherName(otherUser.getChatRoomTitle())
                .chats(chatList)
                .otherUserName(teacherUser.getChatRoomTitle())
                .childName(teacherUser.getChildName())
                .build();
    }

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
        String chatRoomUserCountKey = "CHAT_ROOM_USER_COUNT_" + dto.getChatRoomId();
//        String roomUserCount = redisUtil.getString(chatRoomUserCountKey);
        Long roomUserCount = redisUtil.getSetSize(chatRoomUserCountKey);
        log.debug("[채팅 전송(소켓)] 채팅방 지금 인원수 = {}", roomUserCount);

        // 채팅방 1명임 > 상대한테 알림 보냄
        if (roomUserCount == 1) {
            UserInfo senderUser = userServiceClient.searchUserInfoByUserKey(dto.getSenderUserKey());
            log.debug("[채팅 전송(소켓)] 전송자 = {}", senderUser.getUserName());
            // 채팅방 다른 유저 가져오기
            Long opponentUserId = chatRoomUserQueryRepository.findOpponentUserId(dto.getChatRoomId(), senderUser.getUserId())
                    .orElseThrow(() -> new NoSuchElementException("상대 유저 정보를 찾을 수 없습니다."));
            log.debug("[채팅 전송(소켓)] 수신자Id = {}", opponentUserId);
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

            try {
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

                log.debug("[채팅 전송(소켓)] 알림 보냄");
            } catch (Exception e) {
                log.debug("[채팅 전송(소켓)] 알림 전송 실패. {}", e.getMessage());
            }
        }
    }
}
