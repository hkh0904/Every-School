package com.everyschool.userservice.api.app.controller.user.response;

import com.everyschool.userservice.api.app.controller.user.response.info.School;
import com.everyschool.userservice.api.app.controller.user.response.info.SchoolClass;
import com.everyschool.userservice.domain.user.Student;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentInfoResponse {

    private int userType;
    private String email;
    private String name;
    private String birth;
    private School school;
    private SchoolClass schoolClass;
    private LocalDateTime joinDate;

    @Builder
    private StudentInfoResponse(int userType, String email, String name, String birth, School school, SchoolClass schoolClass, LocalDateTime joinDate) {
        this.userType = userType;
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.school = school;
        this.schoolClass = schoolClass;
        this.joinDate = joinDate;
    }

    public static StudentInfoResponse of(Student student, School school, SchoolClass schoolClass) {
        return StudentInfoResponse.builder()
            .userType(student.getUserCodeId())
            .email(student.getEmail())
            .name(student.getName())
            .birth(student.getBirth())
            .school(school)
            .schoolClass(schoolClass)
            .joinDate(student.getCreatedDate())
            .build();
    }

}
