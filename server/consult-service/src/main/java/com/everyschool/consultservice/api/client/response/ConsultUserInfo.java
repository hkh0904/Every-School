package com.everyschool.consultservice.api.client.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ConsultUserInfo {

    private Long userId;
    private String userInfo;

    @Builder
    public ConsultUserInfo(Long userId, String userInfo) {
        this.userId = userId;
        this.userInfo = userInfo;
    }
}
