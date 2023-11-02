package com.everyschool.chatservice.api.controller.filterword.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ChatFilterResponse {

    private Boolean isBad;
    private String reason;

    @Builder
    private ChatFilterResponse(Boolean isBad, String reason) {
        this.isBad = isBad;
        this.reason = reason;
    }
}