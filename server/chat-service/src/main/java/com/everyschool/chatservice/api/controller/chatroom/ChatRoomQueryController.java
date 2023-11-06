package com.everyschool.chatservice.api.controller.chatroom;

import com.everyschool.chatservice.api.ApiResponse;
import com.everyschool.chatservice.api.controller.chat.response.ChatRoomListResponse;
import com.everyschool.chatservice.api.service.chatroom.ChatRoomQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat-service/v1/chat-rooms")
public class ChatRoomQueryController {
    private final ChatRoomQueryService chatRoomQueryService;

    /**
     * 채팅방 목록 조회
     *
     * @return
     */
    @GetMapping("/chat-room")
    public ApiResponse<List<ChatRoomListResponse>> searchChatRoomList(@RequestHeader("Authorization") String token) {

        List<ChatRoomListResponse> responses = chatRoomQueryService.searchChatRooms(token);
        return ApiResponse.ok(responses);
    }
}
