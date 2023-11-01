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
     * @return
     */
    @MessageMapping("/chat.send")
//    @SendTo("/sub/{roomTopic}")
    public void sendMessage(ChatMessage message) {

        Long roomTopic = message.getChatRoomId();
        // TODO: 2023-10-31 메세지 전송 전 가공하기
        template.convertAndSend("/sub/" + roomTopic, message);
    }
}


// TODO: 2023-11-01 이거 임시 커밋함 해야함