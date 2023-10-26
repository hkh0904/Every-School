package com.everyschool.chatservice.api.client.response;

import lombok.Builder;
import lombok.Data;

@Data
public class UserInfo {

    private Long userId;
    private char userType;  //S:학생, T:교사, M:마더, F:파더
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
