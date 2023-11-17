package com.everyschool.openaiservice.api.client.response.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class Choices {
    private String finishReason;
    private Message message;
    private int index;

    @Builder
    private Choices(String finishReason, Message message, int index) {
        this.finishReason = finishReason;
        this.message = message;
        this.index = index;
    }
}
