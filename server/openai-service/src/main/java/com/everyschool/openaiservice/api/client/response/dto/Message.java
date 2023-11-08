package com.everyschool.openaiservice.api.client.response.dto;

public class Message {
    private String role;
    private String content;

    public String getContent() {
        return content;
    }

    public String getRole() {
        return role;
    }
}
