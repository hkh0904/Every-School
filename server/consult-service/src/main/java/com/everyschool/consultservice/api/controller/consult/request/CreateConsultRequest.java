package com.everyschool.consultservice.api.controller.consult.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateConsultRequest {

    private LocalDateTime startTime;
    private String content;
    private Integer codeId;

    @Builder
    private CreateConsultRequest(LocalDateTime startTime, String content, Integer codeId) {
        this.startTime = startTime;
        this.content = content;
        this.codeId = codeId;
    }
}
