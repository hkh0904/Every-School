package com.everyschool.reportservice.api.web.controller.report.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EditStatusRequest {

    @NotNull(message = "처리 상태 코드는 필수입니다.")
    private Integer status;

    @Builder
    private EditStatusRequest(Integer status) {
        this.status = status;
    }
}
