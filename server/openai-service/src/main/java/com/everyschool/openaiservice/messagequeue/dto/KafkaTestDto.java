package com.everyschool.openaiservice.messagequeue.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class KafkaTestDto {

    private String content;

    @Builder
    private KafkaTestDto(String content) {
        this.content = content;
    }
}
