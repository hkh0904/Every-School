package com.everyschool.openaiservice.api.client.response.dto;

public class Usage {
    private int totalTokens;
    private int completionTokens;
    private int promptTokens;

    public int getTotalTokens() {
        return totalTokens;
    }

    public int getCompletionTokens() {
        return completionTokens;
    }

    public int getPromptTokens() {
        return promptTokens;
    }
}
