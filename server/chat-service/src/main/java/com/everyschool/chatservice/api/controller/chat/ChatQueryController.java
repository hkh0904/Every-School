package com.everyschool.chatservice.api.controller.chat;

import com.everyschool.chatservice.api.ApiResponse;
import com.everyschool.chatservice.api.controller.chat.response.ChatResponse;
import com.everyschool.chatservice.api.service.chat.ChatQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat-service/v1")
public class ChatQueryController {


    private final ChatQueryService chatQueryService;

    /**
     * 채팅 내용 조회
     *
     * @param chatRoomId
     * @return
     */
    @GetMapping("/chat-rooms/{chatRoomId}")
    public ApiResponse<List<ChatResponse>> searchChat(@PathVariable Long chatRoomId,
                                                      @RequestParam Long idx,
                                                      @RequestHeader("Authorization") String token) {

        List<ChatResponse> responses = chatQueryService.searchChat(chatRoomId, idx, token);
        return ApiResponse.ok(responses);
    }
}
