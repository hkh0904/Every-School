package com.everyschool.chatservice.api.controller.chatroom;

import com.everyschool.chatservice.api.ApiResponse;
import com.everyschool.chatservice.api.controller.chatroom.request.CreateChatRoomRequest;
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
public class ChatRoomController {

    private final ChatRoomService chatRoomService;


    /**
     * 채팅방 생성
     *
     * @param request
     * @param token   상대 유저 토큰
     * @return
     */
    @PostMapping("/chat-rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateChatRoomResponse> createChatRoom(@RequestBody @Valid CreateChatRoomRequest request,
                                                              @RequestHeader("Authorization") String token) {

        log.debug("[채팅방 생성 Controller] 요청 들어옴");
        CreateChatRoomDto dto = request.toDto(token);
        CreateChatRoomResponse response = chatRoomService.createChatRoom(dto);
        return ApiResponse.created(response);
    }
}
