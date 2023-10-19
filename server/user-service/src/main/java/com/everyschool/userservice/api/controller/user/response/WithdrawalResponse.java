package com.everyschool.userservice.api.controller.user.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WithdrawalResponse {

    private String email;
    private String name;
    private String type;
    private LocalDateTime withdrawalDate;

    @Builder
    public WithdrawalResponse(String email, String name, String type, LocalDateTime withdrawalDate) {
        this.email = email;
        this.name = name;
        this.type = type;
        this.withdrawalDate = withdrawalDate;
    }
}
