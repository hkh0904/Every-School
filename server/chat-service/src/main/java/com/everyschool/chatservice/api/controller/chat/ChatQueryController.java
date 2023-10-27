package com.everyschool.chatservice.api.controller.chat;

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
@RequestMapping("/chat-service/v1")
public class ChatQueryController {

    private final ChatRoomQueryService chatRoomQueryService;

    /**
     * 채팅방 목록 조회
     *
     * @return
     */
    @GetMapping("/chat-room")
    public ApiResponse<List<ChatRoomListResponse>> searchChatRoomList(@RequestHeader("Authorization") String token) {

        List<ChatRoomListResponse> responses = chatRoomQueryService.searchChatRooms(token);
        // TODO: 2023-10-23 채팅방 목록 조회
//        ChatRoomListResponse response1 = ChatRoomListResponse.builder()
//                .roomId(1L)
//                .roomTitle("신성주 1학년 3반 임우택(부)")
//                .lastMessage("점심 맛있게 드세요~")
//                .updateTime(LocalDateTime.of(2023, 10, 20, 11, 30))
//                .unreadMessageNum(3)
//                .build();
//
//        ChatRoomListResponse response2 = ChatRoomListResponse.builder()
//                .roomId(2L)
//                .roomTitle("손흥민 1학년 3반 (학생)")
//                .lastMessage("축구선수가 되고싶어요")
//                .updateTime(LocalDateTime.of(2023, 10, 20, 11, 34))
//                .unreadMessageNum(8)
//                .build();
//
//        ChatRoomListResponse response3 = ChatRoomListResponse.builder()
//                .roomId(3L)
//                .roomTitle("짱구 1학년 3반 곰돌이(부)")
//                .lastMessage("선풍기 부러졌어요")
//                .updateTime(LocalDateTime.of(2023, 10, 21, 11, 30))
//                .unreadMessageNum(3)
//                .build();
//
//        List<ChatRoomListResponse> responses = List.of(response1, response2, response3);
        return ApiResponse.ok(responses);
    }
}
