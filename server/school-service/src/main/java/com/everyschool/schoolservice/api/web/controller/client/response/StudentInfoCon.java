package com.everyschool.schoolservice.api.web.controller.client.response;

import lombok.Builder;
import lombok.Data;

@Data
public class StudentInfoCon {

    private Long userId;
    private Integer studentNumber;

    @Builder
    public StudentInfoCon(Long userId, Integer studentNumber) {
        this.userId = userId;
        this.studentNumber = studentNumber;
    }
}
