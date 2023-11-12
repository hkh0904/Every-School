package com.everyschool.schoolservice.api.app.controller.schoolapply.request;

import com.everyschool.schoolservice.api.app.service.schoolapply.dto.CreateSchoolApplyDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class CreateSchoolApplyRequest {

    @NotNull(message = "학년 정보는 필수입니다.")
    @Positive(message = "학년 정보는 양수여야 합니다.")
    private Integer grade;

    @NotNull(message = "반 정보는 필수입니다.")
    @Positive(message = "반 정보는 양수여야 합니다.")
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

