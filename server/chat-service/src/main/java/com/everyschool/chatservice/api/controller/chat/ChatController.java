package com.everyschool.chatservice.api.controller.chat;

import com.everyschool.chatservice.api.controller.chat.request.ChatMessage;
import com.everyschool.chatservice.api.service.chat.dto.ChatService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;

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
}
