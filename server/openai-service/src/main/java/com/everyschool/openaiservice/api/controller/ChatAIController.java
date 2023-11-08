package com.everyschool.openaiservice.api.controller;

import com.everyschool.openaiservice.api.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/openai-service/client/v1")
public class ChatAIController {

    private final OpenAiService openAiService;

    @PostMapping("/chat-generate")
    public String doCheckChatting() {
        openAiService.doChecking();
        return LocalDate.now().minusDays(1).toString() + " 채팅 검사 완료";
    }
}
