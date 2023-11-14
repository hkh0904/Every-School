package com.everyschool.openaiservice.api.client;

import com.everyschool.openaiservice.api.client.request.GptRequest;
import com.everyschool.openaiservice.api.client.response.GptResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "gpt-service", url = "https://api.openai.com/v1/chat/completions")
public interface GptServiceClient {

    @PostMapping
    GptResponse requestGpt(@RequestHeader(name = "Authorization") String gptKey,
                           @RequestBody GptRequest request);
}
