package com.everyschool.chatservice.config;

import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.service.chatroom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {

    public final ChatRoomService chatRoomService;
    public final UserServiceClient userServiceClient;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        handleMessage(accessor.getCommand(), accessor, message.getHeaders());

        return message;
    }

    private void handleMessage(StompCommand command, StompHeaderAccessor accessor, MessageHeaders headers) {
        switch (command) {
            case CONNECT:
                log.debug("[소켓] CONNECT");
                break;
            case SUBSCRIBE:
                log.debug("[소켓] 채팅방 구독 해야함");
                Long chatRoomId = connectToChatRoom(accessor, headers);
                log.debug("[소켓] 채팅방 구독한 채팅 방 = {}", chatRoomId);
                break;
            case SEND:
                log.debug("[소켓] SEND");

                break;
            case DISCONNECT:
                log.debug("[소켓] DISCONNECT");
                log.debug("[소켓] 채팅방 인원수 감소");
                disconnectToChatRoom(headers);
                break;
        }
    }

    private void disconnectToChatRoom(MessageHeaders headers) {
        Long chatRoomId = getChatRoomNo(headers);
        log.debug("[소켓] 인원수 감소. 채팅방 Id = {}", chatRoomId);
        chatRoomService.disconnect(chatRoomId);
    }

    private Long connectToChatRoom(StompHeaderAccessor accessor, MessageHeaders headers) {
        Long chatRoomId = getChatRoomNo(headers);
        log.debug("[소켓 연결] 채팅방 번호 = {}", chatRoomId);
        String jwt = accessor.getFirstNativeHeader("Authorization");
        log.debug("[소켓 연결] jwt = {}", jwt);
        UserInfo userInfo = userServiceClient.searchUserInfo(jwt);
        chatRoomService.connectChatRoom(chatRoomId, userInfo.getUserId());
        return chatRoomId;
    }

    private String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1) {
            return destination.substring(lastIndex + 1);
        } else {
            return null;
        }
    }

    // TODO: 2023-11-13 여기하는중 연결 끊어질때 정보 필요 디스커넷이 아니라 구독취소로 바꾸기
    private Long getChatRoomNo(MessageHeaders headers) {
        String roomId = getRoomId(Optional.ofNullable((String)
                headers.get("simpDestination")).orElse("InvalidRoomId"));
        return Long.valueOf(roomId);
    }
}
