package com.everyschool.chatservice.api.controller.chat;

import com.everyschool.chatservice.api.ApiResponse;
import com.everyschool.chatservice.api.controller.chat.request.CreateChatRoomRequest;
import com.everyschool.chatservice.api.controller.chat.response.CreateChatRoomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat-service")
public class ChatController {

    @PostMapping("/chat-room")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateChatRoomResponse> createChatRoom(@RequestBody @Valid CreateChatRoomRequest request) {

        // TODO: 2023-10-23 채팅방 생성
        CreateChatRoomResponse response = CreateChatRoomResponse.builder()
            .roomId(2L)
            .roomTitle("신성주 1학년 3반 임우택(부)")
            .build();
        return ApiResponse.created(response);
    }

    // TODO: 2023-10-23 채팅방 조회
    // TODO: 2023-10-23 메세지 전송
    // TODO: 2023-10-23 메세지 조회
}
