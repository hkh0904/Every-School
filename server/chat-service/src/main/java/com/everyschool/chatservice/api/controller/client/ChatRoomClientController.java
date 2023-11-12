package com.everyschool.chatservice.api.controller.client;

import com.everyschool.chatservice.api.controller.client.response.CheckingChatResponse;
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

    /**
     * date 하루 전 날 채팅이 있던 채팅방 Id 가져오기
     *
     * @param date
     * @return
     */
    @GetMapping
    public List<Long> searchChatRoomIdByDate(@RequestParam(name = "date") LocalDate date) {
        return chatService.searchChatRoomIdByDate(date);
    }

    /**
     * 채팅방 date에 이루어진 채팅 목록과 정보 가져오기
     *
     * @param date
     * @param chatRoomId
     * @return
     */
    @GetMapping("/{chatRoomId}")
    public CheckingChatResponse searchChatByDateAndChatRoomId(@RequestParam(name = "date") LocalDate date,
                                                              @PathVariable(name = "chatRoomId") Long chatRoomId) {
        return chatService.searchChatListForContentCheck(chatRoomId, date);
    }


    @GetMapping("/test")
    public String test(){
        return "채팅서버 소통 됨";
    }
}
