package com.everyschool.chatservice.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("school-service")
public interface SchoolServiceClient {

    @GetMapping
    String searchClassName(@RequestParam Long schoolClassId);
}
