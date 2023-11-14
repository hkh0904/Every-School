package com.everyschool.chatservice.api.controller.chat.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnsubscribeRequest {

    private String userKey;

    @Builder
    private UnsubscribeRequest(String userKey) {
        this.userKey = userKey;
    }
}
