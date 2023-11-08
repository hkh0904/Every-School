package com.everyschool.batchservice.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "openai-service", url = "https://every-school.com/api")
public interface OpenAiServiceClient {

    @PostMapping("/openai-service/client/v1/chat-generate")
    String doCheckChatting();
}
