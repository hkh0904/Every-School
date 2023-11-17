package com.everyschool.openaiservice.api.client.response;

import com.everyschool.openaiservice.api.client.response.dto.Choices;
import com.everyschool.openaiservice.api.client.response.dto.Usage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class GptResponse {
    private String id;
    private String object;
    private int created;
    private String model;
    private List<Choices> choices;
    private Usage usage;

    @Builder
    private GptResponse(String id, String object, int created, String model, List<Choices> choices, Usage usage) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
        this.usage = usage;
    }
}
