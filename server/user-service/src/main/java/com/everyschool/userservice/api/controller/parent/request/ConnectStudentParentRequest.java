package com.everyschool.userservice.api.controller.parent.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConnectStudentParentRequest {

    private String connectCode;

    @Builder
    private ConnectStudentParentRequest(String connectCode) {
        this.connectCode = connectCode;
    }
}
