package com.everyschool.chatservice.api.controller.chat.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatRoomListResponse {

    private Long roomId;
    private String opponentUserName;
    private String opponentUserType;
    private String opponentUserChildName;
    private String lastMessage;
    private LocalDateTime updateTime;
    private int unreadMessageNum;

    @Builder
    public ChatRoomListResponse(Long roomId, String opponentUserName, String opponentUserType, String opponentUserChildName, String lastMessage, LocalDateTime updateTime, int unreadMessageNum) {
        this.roomId = roomId;
        this.opponentUserName = opponentUserName;
        this.opponentUserType = opponentUserType;
        this.opponentUserChildName = opponentUserChildName;
        this.lastMessage = lastMessage;
        this.updateTime = updateTime;
        this.unreadMessageNum = unreadMessageNum;
    }
}
