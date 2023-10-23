package com.everyschool.chatservice.api.controller.chat.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatRoomListResponse {

    private Long roomId;
    private String roomTitle;
    private String lastMessage;
    private LocalDateTime updateTime;
    private int unreadMessageNum;

    @Builder
    private ChatRoomListResponse(Long roomId, String roomTitle, String lastMessage, LocalDateTime updateTime, int unreadMessageNum) {
        this.roomId = roomId;
        this.roomTitle = roomTitle;
        this.lastMessage = lastMessage;
        this.updateTime = updateTime;
        this.unreadMessageNum = unreadMessageNum;
    }
}
