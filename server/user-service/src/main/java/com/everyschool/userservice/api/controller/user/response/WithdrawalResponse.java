package com.everyschool.userservice.api.controller.user.response;

import com.everyschool.userservice.domain.user.User;
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

    public static WithdrawalResponse of(User user) {
        return WithdrawalResponse.builder()
            .email(user.getEmail())
            .name(user.getName())
            .type(user.getName())  // TODO: 2023-10-25 임우택 회원 구분 수정
            .withdrawalDate(user.getLastModifiedDate())
            .build();
    }
}
