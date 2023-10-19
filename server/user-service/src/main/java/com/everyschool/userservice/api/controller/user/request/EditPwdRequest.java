package com.everyschool.userservice.api.controller.user.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditPwdRequest {

    private String currentPwd;
    private String newPwd;

    @Builder
    private EditPwdRequest(String currentPwd, String newPwd) {
        this.currentPwd = currentPwd;
        this.newPwd = newPwd;
    }
}
