package com.everyschool.consultservice.api.client.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SchoolClassInfo {

    private Long teacherId;
    private Integer grade;
    private Integer classNum;

    @Builder
    public SchoolClassInfo(Long teacherId, Integer grade, Integer classNum) {
        this.teacherId = teacherId;
        this.grade = grade;
        this.classNum = classNum;
    }
}
