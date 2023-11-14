package com.everyschool.chatservice.api.controller.chat;

import com.everyschool.chatservice.api.controller.chat.request.ChatMessage;
import com.everyschool.chatservice.api.controller.chat.request.UnsubscribeRequest;
import com.everyschool.chatservice.api.service.chat.dto.ChatService;
import com.everyschool.chatservice.api.service.chatroom.ChatRoomService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;
    private final ChatRoomService chatRoomService;

    /**
     * 채팅 전송
     *
     * @param message
     * @return
     */
    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage message) throws FirebaseMessagingException {

        Long roomId = message.getChatRoomId();
        log.debug("[채팅 전송(소켓)] 채팅방 Id = {}", roomId);

        chatService.sendMessageProcessing(message.toDto());
        log.debug("[채팅 전송(소켓)] 알림 보냈음.");
        template.convertAndSend("/sub/" + roomId, message);
        log.debug("[채팅 전송(소켓)] 소켓 전송 완료. message = {}", message.getMessage());
    }

    /**
     * 채팅방 구독 취소
     *
     * @return
     */
    @MessageMapping("/chat.unsub.{chatRoomId}")
    public void unsubscribe(@DestinationVariable Long chatRoomId,
                            UnsubscribeRequest request) {

        log.debug("[구독 취소] 요청 들어옴. 채팅방 Id = {}", chatRoomId);

        chatRoomService.disconnect(chatRoomId, request.getUserKey());
        log.debug("[구독 취소] 요청 들어옴. 채팅방 Id = {}", chatRoomId);
    }
}
