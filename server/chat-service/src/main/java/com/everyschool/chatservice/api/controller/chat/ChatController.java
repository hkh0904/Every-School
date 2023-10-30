package com.everyschool.chatservice.api.controller.chat;

import com.everyschool.chatservice.api.controller.chat.request.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessage message) {

    }

    /**
     * 채팅 전송
     *
     * @param message
     * @param roomTopic
     * @return
     */
    @MessageMapping("/chat/message")
    @SendTo("/topic/{roomTopic}")
    public void sendMessage(ChatMessage message,
                            @DestinationVariable(value = "roomTopic") String roomTopic) {

        roomTopic = message.getRoomTopic();
        template.convertAndSend("/sub/chat/room" + roomTopic, message);
        // TODO: 2023-10-23 메세지 전송
        // TODO: 2023-10-23 회원 정보 요청하기
    }
}
