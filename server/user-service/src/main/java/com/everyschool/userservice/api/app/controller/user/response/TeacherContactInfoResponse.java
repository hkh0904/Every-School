package com.everyschool.userservice.api.app.controller.user.response;

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
}
