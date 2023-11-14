package com.everyschool.openaiservice.api.client.response.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class Message {
    private String role;
    private String content;

    @Builder
    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
