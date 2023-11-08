package com.everyschool.chatservice.api.controller.chat.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateChatRoomResponse {

    private Long roomId;
    private String opponentUserName;
    private String opponentUserType;
    private String opponentUsersChildName;

    @Builder
    private CreateChatRoomResponse(Long roomId, String opponentUserName, String opponentUserType, String opponentUsersChildName) {
        this.roomId = roomId;
        this.opponentUserName = opponentUserName;
        this.opponentUserType = opponentUserType;
        this.opponentUsersChildName = opponentUsersChildName;
    }
}
