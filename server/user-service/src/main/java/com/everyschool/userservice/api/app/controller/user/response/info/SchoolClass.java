package com.everyschool.userservice.api.app.controller.user.response.info;

import com.everyschool.userservice.api.client.school.response.DescendantInfo;
import com.everyschool.userservice.api.client.school.response.SchoolClassInfo;
import lombok.Builder;
import lombok.Data;

@Data
public class SchoolClass {

    private Long schoolClassId;
    private Integer schoolYear;
    private Integer grade;
    private Integer classNum;

    @Builder
    private SchoolClass(Long schoolClassId, Integer schoolYear, Integer grade, Integer classNum) {
        this.schoolClassId = schoolClassId;
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.classNum = classNum;
    }

    public static SchoolClass of(Long schoolClassId, SchoolClassInfo schoolClassInfo) {
        return SchoolClass.builder()
            .schoolClassId(schoolClassId)
            .schoolYear(schoolClassInfo.getSchoolYear())
            .grade(schoolClassInfo.getGrade())
            .classNum(schoolClassInfo.getClassNum())
            .build();
    }

    public static SchoolClass of(Long schoolClassId, DescendantInfo descendantInfo) {
        return SchoolClass.builder()
            .schoolClassId(schoolClassId)
            .schoolYear(descendantInfo.getSchoolYear())
            .grade(descendantInfo.getGrade())
            .classNum(descendantInfo.getClassNum())
            .build();
    }
}
