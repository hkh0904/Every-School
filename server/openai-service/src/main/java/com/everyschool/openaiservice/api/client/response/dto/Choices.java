package com.everyschool.openaiservice.api.client.response.dto;

public class Choices {
    private String finishReason;
    private Message message;
    private int index;

    public String getFinishReason() {
        return finishReason;
    }

    public Message getMessage() {
        return message;
    }

    public int getIndex() {
        return index;
    }
}
