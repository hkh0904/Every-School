package com.everyschool.chatservice.api.controller.chat;

import com.everyschool.chatservice.api.ApiResponse;
import com.everyschool.chatservice.api.controller.chat.response.ChatResponse;
import com.everyschool.chatservice.api.controller.chat.response.WarningChat;
import com.everyschool.chatservice.api.controller.chat.response.WarningChatResponse;
import com.everyschool.chatservice.api.controller.chat.response.WarningChatReviewResponse;
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

    /**
     * 채팅 리뷰
     *
     * @param token
     * @return
     */
    @GetMapping("/chat-review")
    public ApiResponse<List<WarningChatReviewResponse>> searchReviewChatList(@RequestHeader("Authorization") String token) {
        List<WarningChatReviewResponse> responses = chatQueryService.searchReviewChatList(token);
        return ApiResponse.ok(responses);
    }

    /**
     * 채팅 상세 조회
     *
     * @param date
     * @return
     */
    @GetMapping("/chat-review/{chatRoomId}/{reviewId}")
    public ApiResponse<WarningChatResponse> searchReviewChat(@RequestHeader("Authorization") String token,
                                                             @PathVariable Long chatRoomId,
                                                             @PathVariable Long reviewId,
                                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        WarningChatResponse responses = chatQueryService.searchReviewChat(chatRoomId, reviewId, date, token);
        return ApiResponse.ok(responses);
    }
}
