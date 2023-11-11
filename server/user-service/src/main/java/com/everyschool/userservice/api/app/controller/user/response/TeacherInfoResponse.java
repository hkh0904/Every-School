package com.everyschool.userservice.api.app.controller.user.response;

import com.everyschool.userservice.api.app.controller.user.response.info.School;
import com.everyschool.userservice.api.app.controller.user.response.info.SchoolClass;
import com.everyschool.userservice.domain.user.Teacher;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeacherInfoResponse {

    private int userType;
    private String email;
    private String name;
    private String birth;
    private School school;
    private SchoolClass schoolClass;
    private LocalDateTime joinDate;

    @Builder
    private TeacherInfoResponse(int userType, String email, String name, String birth, School school, SchoolClass schoolClass, LocalDateTime joinDate) {
        this.userType = userType;
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.school = school;
        this.schoolClass = schoolClass;
        this.joinDate = joinDate;
    }

    public static TeacherInfoResponse of(Teacher teacher, School school, SchoolClass schoolClass) {
        return TeacherInfoResponse.builder()
            .userType(teacher.getUserCodeId())
            .email(teacher.getEmail())
            .name(teacher.getName())
            .birth(teacher.getBirth())
            .school(school)
            .schoolClass(schoolClass)
            .joinDate(teacher.getCreatedDate())
            .build();
    }
}
