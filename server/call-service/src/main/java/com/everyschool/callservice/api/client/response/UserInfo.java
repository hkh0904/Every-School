package com.everyschool.callservice.api.client.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserInfo {

    private Long userId;
    private char userType;      //S:학생, T:교사, M:마더, F:파더
    private String userName;
    private Long schoolClassId; //학급 키(학생, 교사일 경우만)

    @Builder
    private UserInfo(Long userId, char userType, String userName, Long schoolClassId) {
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.schoolClassId = schoolClassId;
    }
}
