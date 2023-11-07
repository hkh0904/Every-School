package com.everyschool.schoolservice.api.app.controller.schoolapply.request;

import com.everyschool.schoolservice.api.app.service.schoolapply.dto.CreateSchoolApplyDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateSchoolApplyRequest {

    private Integer grade;
    private Integer classNum;

    @Builder
    private CreateSchoolApplyRequest(Integer grade, Integer classNum) {
        this.grade = grade;
        this.classNum = classNum;
    }

    public CreateSchoolApplyDto toDto() {
        return CreateSchoolApplyDto.builder()
            .grade(this.grade)
            .classNum(this.classNum)
            .build();
    }
}

