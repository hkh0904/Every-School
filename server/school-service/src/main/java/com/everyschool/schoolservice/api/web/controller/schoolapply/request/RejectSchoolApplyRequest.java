package com.everyschool.schoolservice.api.web.controller.schoolapply.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RejectSchoolApplyRequest {

    private String rejectedReason;

    @Builder
    private RejectSchoolApplyRequest(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }
}
