package com.everyschool.openaiservice.api.client.request;

import com.everyschool.openaiservice.api.client.response.dto.Message;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class GptRequest {
    private String model;
    private List<Message> messages;

    @Builder
    private GptRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }
}
