package com.everyschool.openaiservice.api.client.request;

import java.util.List;

public abstract class GptRequest {
    private String model;
    private List<Messages> messages;

    public List<Messages> getMessages() {
        return messages;
    }

    public String getModel() {
        return model;
    }
}
