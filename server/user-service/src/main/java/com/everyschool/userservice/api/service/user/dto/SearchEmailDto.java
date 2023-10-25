package com.everyschool.userservice.api.service.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SearchEmailDto {

    private String email;
    private boolean isDeleted;

    @Builder
    public SearchEmailDto(String email, Boolean isDeleted) {
        this.email = email;
        this.isDeleted = isDeleted;
    }
}
