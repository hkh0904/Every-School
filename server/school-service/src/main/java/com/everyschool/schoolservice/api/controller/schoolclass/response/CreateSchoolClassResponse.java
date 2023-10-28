package com.everyschool.schoolservice.api.controller.schoolclass.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateSchoolClassResponse {

    private Long schoolClassId;
    private String schoolName;
    private Integer schoolYear;
    private Integer grade;
    private Integer classNum;
    private String teacherName;
    private LocalDateTime createdDate;
}
