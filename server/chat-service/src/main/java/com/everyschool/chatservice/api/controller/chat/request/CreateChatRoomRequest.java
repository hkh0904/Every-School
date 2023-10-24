package com.everyschool.chatservice.api.controller.chat.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateChatRoomRequest {

    private String userKey;
    private String type;

    @Builder
    private CreateChatRoomRequest(String userKey, String type) {
        this.userKey = userKey;
        this.type = type;
    }
}
