package com.everyschool.schoolservice.api.controller.schoolclass.response;

import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import lombok.Builder;
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

    @Builder
    public CreateSchoolClassResponse(Long schoolClassId, String schoolName, Integer schoolYear, Integer grade, Integer classNum, String teacherName, LocalDateTime createdDate) {
        this.schoolClassId = schoolClassId;
        this.schoolName = schoolName;
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.classNum = classNum;
        this.teacherName = teacherName;
        this.createdDate = createdDate;
    }

    public static CreateSchoolClassResponse of(SchoolClass schoolClass) {
        return CreateSchoolClassResponse.builder()
            .schoolClassId(schoolClass.getId())
            .schoolName(schoolClass.getSchool().getName())
            .schoolYear(schoolClass.getSchoolYear())
            .grade(schoolClass.getGrade())
            .classNum(schoolClass.getClassNum())
            .teacherName(null)
            .createdDate(schoolClass.getCreatedDate())
            .build();
    }
}
