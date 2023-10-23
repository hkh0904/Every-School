package com.everyschool.chatservice.api.controller.chat;

import com.everyschool.chatservice.api.ApiResponse;
import com.everyschool.chatservice.api.controller.chat.request.CreateChatRoomRequest;
import com.everyschool.chatservice.api.controller.chat.response.ChatRoomListResponse;
import com.everyschool.chatservice.api.controller.chat.response.CreateChatRoomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat-service")
public class ChatController {

    @PostMapping("/chat-room")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateChatRoomResponse> createChatRoom(@RequestBody @Valid CreateChatRoomRequest request) {

        // TODO: 2023-10-23 회원 정보 요청
        // TODO: 2023-10-23 채팅방 생성
        CreateChatRoomResponse response = CreateChatRoomResponse.builder()
            .roomId(2L)
            .roomTitle("신성주 1학년 3반 임우택(부)")
            .build();
        return ApiResponse.created(response);
    }

    @GetMapping("/chat-room/{userKey}")
    public ApiResponse<List<ChatRoomListResponse>> searchChatRoomList(@PathVariable String userKey) {


        // TODO: 2023-10-23 채팅방 목록 조회
        ChatRoomListResponse response1 = ChatRoomListResponse.builder()
            .roomId(1L)
            .roomTitle("신성주 1학년 3반 임우택(부)")
            .lastMessage("점심 맛있게 드세요~")
            .updateTime(LocalDateTime.of(2023, 10, 20, 11, 30))
            .unreadMessageNum(3)
            .build();

        ChatRoomListResponse response2 = ChatRoomListResponse.builder()
            .roomId(2L)
            .roomTitle("손흥민 1학년 3반 (학생)")
            .lastMessage("축구선수가 되고싶어요")
            .updateTime(LocalDateTime.of(2023, 10, 20, 11, 34))
            .unreadMessageNum(8)
            .build();

        ChatRoomListResponse response3 = ChatRoomListResponse.builder()
            .roomId(3L)
            .roomTitle("짱구 1학년 3반 곰돌이(부)")
            .lastMessage("선풍기 부러졌어요")
            .updateTime(LocalDateTime.of(2023, 10, 21, 11, 30))
            .unreadMessageNum(3)
            .build();

        List<ChatRoomListResponse> responses = List.of(response1, response2, response3);
        return ApiResponse.ok(responses);
    }

    @PostMapping("/chat-room/{chatRoomId}")
    public ApiResponse<Long> sendMessage(@PathVariable String chatRoomId) {

        // TODO: 2023-10-23 메세지 전송
        // TODO: 2023-10-23 회원 정보 요청하기
        return ApiResponse.ok(1L);
    }

    // TODO: 2023-10-23 메세지 조회
}
