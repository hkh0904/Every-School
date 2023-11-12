package com.everyschool.schoolservice.api.web.controller.schoolapply.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RejectSchoolApplyRequest {

    @NotBlank(message = "거절 사유는 필수입니다.")
    @Size(max = 50, message = "거절 사유는 최대 50자 입니다.")
    private String rejectedReason;

    @Builder
    private RejectSchoolApplyRequest(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }
}
