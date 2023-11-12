package com.everyschool.consultservice.api.web.controller.consult.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class RejectConsultRequest {

    @NotEmpty
    private String rejectedReason;

    @Builder
    private RejectConsultRequest(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }
}
