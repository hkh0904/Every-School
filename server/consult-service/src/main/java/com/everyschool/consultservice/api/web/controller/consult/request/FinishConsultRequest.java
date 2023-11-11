package com.everyschool.consultservice.api.web.controller.consult.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class FinishConsultRequest {

    @NotEmpty
    private String resultContent;

    @Builder
    private FinishConsultRequest(String resultContent) {
        this.resultContent = resultContent;
    }
}
