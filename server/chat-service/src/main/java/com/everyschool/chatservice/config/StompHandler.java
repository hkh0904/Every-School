package com.everyschool.chatservice.config;

import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.service.chatroom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {

    public final ChatRoomService chatRoomService;
    public final UserServiceClient userServiceClient;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        handleMessage(accessor.getCommand(), accessor);

        return message;
    }

    private void handleMessage(StompCommand command, StompHeaderAccessor accessor) {
        switch (command){
            case CONNECT:
                log.debug("[소켓] CONNECT");
                connectToChatRoom(accessor);
                break;
            case SUBSCRIBE:
                log.debug("[소켓] SUBSCRIBE");
                break;
            case SEND:
                // TODO: 2023-11-01 읽음처리 해야함
                // TODO: 2023-11-01 알림 보내기도 해야함
                log.debug("[소켓] SEND");
                break;
            case DISCONNECT:
                log.debug("[소켓] DISCONNECT");
                break;
        }
    }

    private void connectToChatRoom(StompHeaderAccessor accessor) {
        Long chatRoomId = getChatRoomNo(accessor);
        String jwt = accessor.getFirstNativeHeader("Auth");
        UserInfo userInfo = userServiceClient.searchUserInfo(jwt);
        chatRoomService.connectChatRoom(chatRoomId, userInfo.getUserId());
    }

    private Long getChatRoomNo(StompHeaderAccessor accessor) {
        return null;
    }
}


// TODO: 2023-11-01 이거 임시 커밋함 해야함