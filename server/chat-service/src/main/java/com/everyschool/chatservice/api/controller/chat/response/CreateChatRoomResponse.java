package com.everyschool.chatservice.api.controller.chat.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateChatRoomResponse {

    private Long roomId;
    private String roomTitle;

    @Builder
    private CreateChatRoomResponse(Long roomId, String roomTitle) {
        this.roomId = roomId;
        this.roomTitle = roomTitle;
    }
}
