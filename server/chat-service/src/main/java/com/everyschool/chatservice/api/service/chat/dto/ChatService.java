package com.everyschool.chatservice.api.service.chat.dto;

import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.service.util.RedisUtils;
import com.everyschool.chatservice.domain.chatroomuser.repository.ChatRoomUserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final RedisUtils redisUtil;

    private final ChatRoomUserQueryRepository chatRoomUserQueryRepository;

    private final UserServiceClient userServiceClient;

    public void sendMessageProcessing(SendMessageDto dto) {
        // 채팅방 인원수 확인
        String chatRoomUserCountKey = "CHAR_ROOM_USER_COUNT_" + dto.getChatRoomId();
        String roomUserCount = redisUtil.getString(chatRoomUserCountKey);

        // 채팅방 1명임 > 상대한테 알림 보냄
        if (roomUserCount != null && Integer.parseInt(roomUserCount) == 1) {
            UserInfo senderUser = userServiceClient.searchUserInfoByUserKey(dto.getSenderUserKey());
            // 채팅방 다른 유저 가져오기
            Long opponentUserId = chatRoomUserQueryRepository.findOpponentUserId(dto.getChatRoomId(), senderUser.getUserId())
                    .orElseThrow(() -> new NoSuchElementException("상대 유저 정보를 찾을 수 없습니다."));
            // TODO: 2023-11-02 해당 유저한테 알림 보내기

        }
    }
}
