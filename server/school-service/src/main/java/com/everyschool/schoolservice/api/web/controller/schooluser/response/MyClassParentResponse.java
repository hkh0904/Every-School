package com.everyschool.schoolservice.api.web.controller.schooluser.response;

import lombok.Builder;
import lombok.Data;

@Data
public class MyClassParentResponse {

    private Long userId;
    private Integer studentNumber;
    private String studentName;
    private String parentType;
    private String name;

    @Builder
    public MyClassParentResponse(Long userId, Integer studentNumber, String studentName, char parentType, String name) {
        this.userId = userId;
        this.studentNumber = studentNumber;
        this.studentName = studentName;
        this.parentType = parentType == 'M' ? "아버님" : "어머님";
        this.name = name;
    }
}
