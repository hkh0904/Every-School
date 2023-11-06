package com.everyschool.reportservice.api.client.response;

import lombok.Builder;
import lombok.Data;

@Data
public class UserInfo {

    private Long userId;
    private char userType;
    private String userName;
    private Long schoolClassId;

    @Builder
    private UserInfo(Long userId, char userType, String userName, Long schoolClassId) {
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.schoolClassId = schoolClassId;
    }

}
