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

    @GetMapping("/chat-room/{chatRoomId}")
    public ApiResponse<List<ChatResponse>> searchChat(@PathVariable Long chatRoomId) {

        // TODO: 2023-10-23 메세지 조회
        ChatResponse chat1 = ChatResponse.builder()
                .chatId(1L)
                .isMine(true)
                .content("선생님 오늘 퇴근 언제하시나요?")
                .sendTime(LocalDateTime.of(2023, 10, 21, 11, 30))
                .build();
        ChatResponse chat2 = ChatResponse.builder()
                .chatId(2L)
                .isMine(false)
                .content("오늘 늦게 합니다")
                .sendTime(LocalDateTime.of(2023, 10, 21, 11, 31))
                .build();
        ChatResponse chat3 = ChatResponse.builder()
                .chatId(3L)
                .isMine(true)
                .content("선생님 오늘 점심은 뭔가요?")
                .sendTime(LocalDateTime.of(2023, 10, 21, 11, 35))
                .build();
        ChatResponse chat4 = ChatResponse.builder()
                .chatId(4L)
                .isMine(true)
                .content("선생님 피자는 안나오나요?")
                .sendTime(LocalDateTime.of(2023, 10, 21, 11, 35))
                .build();
        ChatResponse chat5 = ChatResponse.builder()
                .chatId(5L)
                .isMine(false)
                .content("오늘 밥 맛 없어요")
                .sendTime(LocalDateTime.of(2023, 10, 21, 11, 50))
                .build();

        List<ChatResponse> responses = List.of(chat1, chat2, chat3, chat4, chat5);
        return ApiResponse.ok(responses);
    }
}
