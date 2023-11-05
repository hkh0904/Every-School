package com.everyschool.userservice.api.app.controller.user.response.info;

import lombok.Builder;
import lombok.Data;

@Data
public class School {

    private Long schoolId;
    private String name;

    @Builder
    private School(Long schoolId, String name) {
        this.schoolId = schoolId;
        this.name = name;
    }
}
