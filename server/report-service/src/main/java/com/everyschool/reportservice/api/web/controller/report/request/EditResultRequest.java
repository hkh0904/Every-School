package com.everyschool.reportservice.api.web.controller.report.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class EditResultRequest {

    @NotEmpty(message = "신고 처리 결과는 필수입니다.")
    @Size(max = 500, message = "신고 처리 결과는 최대 500자 입니다.")
    private String result;

    @Builder
    public EditResultRequest(String result) {
        this.result = result;
    }
}
