package com.everyschool.userservice.api.controller.client.response;

import com.everyschool.userservice.domain.user.Parent;
import com.everyschool.userservice.domain.user.Student;
import com.everyschool.userservice.domain.user.Teacher;
import lombok.Builder;
import lombok.Data;

@Data
public class UserInfo {

    private Long userId;
    private char userType;
    private String userName;
    private Long schoolClassId;

    @Builder
    private UserInfo(Long userId, char userType, String userName, Long schoolClassId) {
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.schoolClassId = schoolClassId;
    }

    public static UserInfo of(Student student) {
        return UserInfo.builder()
            .userId(student.getId())
            .userType('S')
            .userName(student.getName())
            .schoolClassId(student.getSchoolClassId())
            .build();
    }

    public static UserInfo of(Parent parent) {
        return UserInfo.builder()
            .userId(parent.getId())
            .userType(parent.getParentType().charAt(0))
            .userName(parent.getName())
            .schoolClassId(null)
            .build();
    }

    public static UserInfo of(Teacher teacher) {
        return UserInfo.builder()
            .userId(teacher.getId())
            .userType('T')
            .userName(teacher.getName())
            .schoolClassId(teacher.getSchoolClassId())
            .build();
    }

}
