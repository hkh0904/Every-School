package com.everyschool.openaiservice.api.client.response;

import com.everyschool.openaiservice.api.client.response.dto.Choices;
import com.everyschool.openaiservice.api.client.response.dto.Usage;

import java.util.List;

public abstract class GptResponse {
    private String id;
    private String object;
    private int created;
    private String model;
    private List<Choices> choices;
    private Usage usage;

    public Usage getUsage() {
        return usage;
    }

    public List<Choices> getChoices() {
        return choices;
    }

    public String getModel() {
        return model;
    }

    public int getCreated() {
        return created;
    }

    public String getObject() {
        return object;
    }

    public String getId() {
        return id;
    }
}
