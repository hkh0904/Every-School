package com.everyschool.userservice.messagequeue.dto;

import com.everyschool.userservice.domain.user.Parent;
import com.everyschool.userservice.domain.user.Student;
import lombok.Builder;
import lombok.Data;

@Data
public class ParentSchoolApplyDto {

    private Long parentId;
    private Long studentId;
    private Long schoolClassId;

    @Builder
    private ParentSchoolApplyDto(Long parentId, Long studentId, Long schoolClassId) {
        this.parentId = parentId;
        this.studentId = studentId;
        this.schoolClassId = schoolClassId;
    }

    public static ParentSchoolApplyDto of(Student student, Parent parent) {
        return ParentSchoolApplyDto.builder()
            .parentId(parent.getId())
            .studentId(student.getId())
            .schoolClassId(student.getSchoolClassId())
            .build();
    }
}
