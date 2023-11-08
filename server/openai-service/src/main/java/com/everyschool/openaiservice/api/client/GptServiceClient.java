package com.everyschool.openaiservice.api.client;

import com.everyschool.openaiservice.api.client.response.GptResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "https://api.openai.com/v1")
public interface GptServiceClient {

    @PostMapping("/chat/completions")
    GptResponse requestGpt(@RequestHeader("Authorization") String accessToken);
}
