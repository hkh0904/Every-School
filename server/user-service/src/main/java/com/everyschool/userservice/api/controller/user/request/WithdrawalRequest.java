package com.everyschool.userservice.api.controller.user.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WithdrawalRequest {

    private String pwd;

    @Builder
    private WithdrawalRequest(String pwd) {
        this.pwd = pwd;
    }
}
