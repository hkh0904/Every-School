package com.everyschool.chatservice.api.controller.chat.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateChatRoomResponse {

    private Long roomId;
    private String roomTitle;
    private String userName;

    @Builder
    private CreateChatRoomResponse(Long roomId, String roomTitle, String userName) {
        this.roomId = roomId;
        this.roomTitle = roomTitle;
        this.userName = userName;
    }
}
