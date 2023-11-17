package com.everyschool.chatservice.api.controller.filterword;

import com.everyschool.chatservice.api.ApiResponse;
import com.everyschool.chatservice.api.controller.chat.request.ChatMessage;
import com.everyschool.chatservice.api.controller.filterword.request.CreateFilterWordRequest;
import com.everyschool.chatservice.api.controller.filterword.response.ChatFilterResponse;
import com.everyschool.chatservice.api.service.filterword.FilterWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat-service/v1/filters")
public class FilterWordController {

    private final FilterWordService filterWordService;

    /**
     * 필터링 단어 등록
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Long> createFilterWord(@RequestBody @Valid CreateFilterWordRequest request,
                                              @RequestHeader("Authorization") String token) {

        Long filterWordId = filterWordService.createFilterWord(request.toDto(token));
        return ApiResponse.created(filterWordId);
    }

    /**
     * 채팅 보내기 전 필터링 검사, 채팅 저장
     *
     * @param message
     * @return
     */
    @PostMapping("/chat")
    public ApiResponse<ChatFilterResponse> checkMessageFilter(@RequestBody ChatMessage message) {

        log.debug("[Controller] 채팅 필터링 적용. senderUserKey = {}", message.getSenderUserKey());
        ChatFilterResponse response = filterWordService.sendMessage(message);
        return ApiResponse.ok(response);
    }
}
