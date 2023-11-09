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

        chatService.sendMessageProcessing(message.toDto());

        template.convertAndSend("/sub/" + roomId, message);
    }
}
