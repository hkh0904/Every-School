package com.everyschool.schoolservice.api.controller.client.response;

import com.everyschool.schoolservice.domain.schooluser.SchoolUser;
import com.everyschool.schoolservice.domain.schooluser.UserType;
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

    public static ConsultUserInfo of(SchoolUser schoolUser) {
        return ConsultUserInfo.builder()
            .userId(schoolUser.getUserId())
            .userInfo(getUserInfo(schoolUser))
            .build();
    }

    private static String getUserInfo(SchoolUser schoolUser) {
        if (schoolUser.getStudentNumber() != null) {
            return String.format("%d %s 학생", schoolUser.getStudentNumber(), schoolUser.getUserName());
        }

        return String.format("%s %s", schoolUser.getUserName(), UserType.getText(schoolUser.getUserTypeId()));
    }
}
