package com.everyschool.chatservice.api.controller.chat.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class WarningChatResponse {

    private String title;
    private List<WarningChat> chatList;

    @Builder
    public WarningChatResponse(String title, List<WarningChat> chatList) {
        this.title = title;
        this.chatList = chatList;
    }
}
