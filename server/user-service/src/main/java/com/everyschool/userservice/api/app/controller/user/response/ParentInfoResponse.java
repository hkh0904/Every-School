package com.everyschool.userservice.api.app.controller.user.response;

import com.everyschool.userservice.api.app.controller.user.response.info.School;
import com.everyschool.userservice.api.app.controller.user.response.info.SchoolClass;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ParentInfoResponse {

    private int userType;
    private String email;
    private String name;
    private String birth;
    private List<Descendant> descendants;
    private LocalDateTime joinDate;

    @Builder
    private ParentInfoResponse(int userType, String email, String name, String birth, List<Descendant> descendants, LocalDateTime joinDate) {
        this.userType = userType;
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.descendants = descendants;
        this.joinDate = joinDate;
    }

    @Data
    public static class Descendant {

        private int userType;
        private String name;
        private Integer studentNumber;
        private School school;
        private SchoolClass schoolClass;

        @Builder
        private Descendant(int userType, String name, Integer studentNumber, School school, SchoolClass schoolClass) {
            this.userType = userType;
            this.name = name;
            this.studentNumber = studentNumber;
            this.school = school;
            this.schoolClass = schoolClass;
        }
    }
}
