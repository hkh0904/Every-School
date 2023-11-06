package com.everyschool.chatservice.api.controller.chat;

import com.everyschool.chatservice.api.ApiResponse;
import com.everyschool.chatservice.api.controller.chat.request.CreateChatRoomRequest;
import com.everyschool.chatservice.api.controller.chat.response.ChatResponse;
import com.everyschool.chatservice.api.controller.chat.response.CreateChatRoomResponse;
import com.everyschool.chatservice.api.service.chatroom.ChatRoomService;
import com.everyschool.chatservice.api.service.chatroom.dto.CreateChatRoomDto;
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
@RequestMapping("/chat-service/v1")
public class ChatController {

    private final ChatRoomService chatRoomService;


    /**
     * 채팅방 생성
     *
     * @param request
     * @param token   상대 유저 토큰
     * @return 채팅방 Id, 채팅방 제목, 상대 유저 이름
     */
    @PostMapping("/chat-room")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateChatRoomResponse> createChatRoom(@RequestBody @Valid CreateChatRoomRequest request,
                                                              @RequestHeader("Authorization") String token) {

        CreateChatRoomDto dto = request.toDto(token);
        CreateChatRoomResponse response = chatRoomService.createChatRoom(dto);
        return ApiResponse.created(response);
    }

    @PostMapping("/chat-room/{chatRoomId}")
    public ApiResponse<Long> sendMessage(@PathVariable String chatRoomId) {

        // TODO: 2023-10-23 메세지 전송
        // TODO: 2023-10-23 회원 정보 요청하기
        return ApiResponse.ok(1L);
    }
}
