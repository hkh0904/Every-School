package com.everyschool.chatservice.api.controller.chat;

import com.everyschool.chatservice.api.ApiResponse;
import com.everyschool.chatservice.api.controller.chat.response.ChatResponse;
import com.everyschool.chatservice.api.controller.chat.response.ChatReviewResponse;
import com.everyschool.chatservice.api.service.chat.ChatQueryService;
import com.mongodb.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
                                                      @RequestParam @Nullable Long idx,
                                                      @RequestHeader("Authorization") String token) {
        log.debug("[Controller] 채팅 목록 불러오기 요청됨. 채팅방 ID = {}", chatRoomId);
        List<ChatResponse> responses = chatQueryService.searchChat(chatRoomId, idx, token);
        return ApiResponse.ok(responses);
    }

    @GetMapping("/chat-review")
    public ApiResponse<List<ChatReviewResponse>> searchChatReviewDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<ChatReviewResponse> chatReviewResponses = chatQueryService.searchReviewChatDate(date);
        return ApiResponse.ok(chatReviewResponses);
    }

    // TODO: 2023/11/13 채팅 상세 조회
}
