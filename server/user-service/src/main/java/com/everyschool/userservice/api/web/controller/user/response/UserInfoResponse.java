package com.everyschool.userservice.api.web.controller.user.response;

import com.everyschool.userservice.api.client.school.response.SchoolClassInfo;
import com.everyschool.userservice.domain.user.Teacher;
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

    public static UserInfoResponse of(Teacher teacher, School school, SchoolClass schoolClass) {
        return UserInfoResponse.builder()
            .userType(teacher.getUserCodeId())
            .email(teacher.getEmail())
            .name(teacher.getName())
            .birth(teacher.getBirth())
            .school(school)
            .schoolClass(schoolClass)
            .joinDate(teacher.getCreatedDate())
            .build();
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

        public static School of(Long schoolId, String name) {
            return School.builder()
                .schoolId(schoolId)
                .name(name)
                .build();
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

        public static SchoolClass of(Long schoolClassId, SchoolClassInfo schoolClassInfo) {
            return SchoolClass.builder()
                .schoolClassId(schoolClassId)
                .grade(schoolClassInfo.getGrade())
                .classNum(schoolClassInfo.getClassNum())
                .build();
        }
    }
}
