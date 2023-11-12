package com.everyschool.userservice.api.controller.parent.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ConnectStudentParentRequest {

    @NotBlank(message = "연결 코드는 필수입니다.")
    private String connectCode;

    @Builder
    private ConnectStudentParentRequest(String connectCode) {
        this.connectCode = connectCode;
    }
}
