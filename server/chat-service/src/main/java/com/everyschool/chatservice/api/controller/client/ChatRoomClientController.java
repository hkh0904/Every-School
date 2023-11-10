package com.everyschool.chatservice.api.controller.client;

import com.everyschool.chatservice.api.service.chat.dto.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    // TODO: 2023-11-10 해야함
    @GetMapping("/{chatRoomId}")
    public ? searchChatByDateAndChatRoomId(@RequestParam(name = "date") LocalDate date,
                                                @PathVariable(name = "chatRoomId") Long chatRoomId) {
        return chatService.searchChatListForContentCheck(chatRoomId, date);
    }
}
