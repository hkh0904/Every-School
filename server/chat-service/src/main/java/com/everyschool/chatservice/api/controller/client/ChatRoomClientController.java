package com.everyschool.chatservice.api.controller.client;

import com.everyschool.chatservice.api.service.chat.dto.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat-service/client/v1/chat-rooms")
public class ChatRoomClientController {

    private final ChatService chatService;

    @GetMapping
    public List<Long> searchChatRoomIdByDate(@RequestParam(name = "date") LocalDate date) {
        return chatService.searchChatRoomIdByDate(date);
    }
}
