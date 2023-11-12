package com.everyschool.userservice.api.app.controller.user.response;

import com.everyschool.userservice.domain.user.User;
import lombok.Builder;
import lombok.Data;

@Data
public class TeacherContactInfoResponse {

    private String userKey;
    private String name;
    private String userType;

    @Builder
    private TeacherContactInfoResponse(String userKey, String name) {
        this.userKey = userKey;
        this.name = name;
        this.userType = "T";
    }

    public static TeacherContactInfoResponse of(User user) {
        return TeacherContactInfoResponse.builder()
            .userKey(user.getUserKey())
            .name(user.getName())
            .build();
    }
}
