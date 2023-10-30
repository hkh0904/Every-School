package com.everyschool.schoolservice.api.controller.schoolclass.request;

import com.everyschool.schoolservice.api.service.schoolclass.dto.CreateSchoolClassDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateSchoolClassRequest {

    private String userKey;
    private Integer schoolYear;
    private Integer grade;
    private Integer classNum;

    @Builder
    private CreateSchoolClassRequest(String userKey, Integer schoolYear, Integer grade, Integer classNum) {
        this.userKey = userKey;
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.classNum = classNum;
    }

    public CreateSchoolClassDto toDto() {
        return CreateSchoolClassDto.builder()
            .userKey(this.userKey)
            .schoolYear(this.schoolYear)
            .grade(this.grade)
            .classNum(this.classNum)
            .build();
    }
}
