package com.everyschool.userservice.api.web.controller.user.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoResponse {

    private Integer userType;
    private String email;
    private String name;
    private String birth;
    private School school;
    private SchoolClass schoolClass;
    private LocalDateTime joinDate;

    @Builder
    private UserInfoResponse(Integer userType, String email, String name, String birth, School school, SchoolClass schoolClass, LocalDateTime joinDate) {
        this.userType = userType;
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.school = school;
        this.schoolClass = schoolClass;
        this.joinDate = joinDate;
    }

    @Data
    public static class School {

        private Long schoolId;
        private String name;

        @Builder
        private School(Long schoolId, String name) {
            this.schoolId = schoolId;
            this.name = name;
        }
    }

    @Data
    public static class SchoolClass {

        private Long schoolClassId;
        private Integer grade;
        private Integer classNum;

        @Builder
        private SchoolClass(Long schoolClassId, Integer grade, Integer classNum) {
            this.schoolClassId = schoolClassId;
            this.grade = grade;
            this.classNum = classNum;
        }
    }
}
