package com.everyschool.openaiservice.api.client.response.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class Usage {
    private int totalTokens;
    private int completionTokens;
    private int promptTokens;

    @Builder
    private Usage(int totalTokens, int completionTokens, int promptTokens) {
        this.totalTokens = totalTokens;
        this.completionTokens = completionTokens;
        this.promptTokens = promptTokens;
    }
}
