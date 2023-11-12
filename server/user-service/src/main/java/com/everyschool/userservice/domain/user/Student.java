package com.everyschool.userservice.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "S")
public class Student extends User {

    private Long schoolId;
    private Long schoolClassId;

    @Builder
    private Student(String email, String pwd, String name, String birth, String userKey, int userCodeId, Long schoolId, Long schoolClassId) {
        super(email, pwd, name, birth, userKey, userCodeId);
        this.schoolId = schoolId;
        this.schoolClassId = schoolClassId;
    }

    //== 비즈니스 로직 ==//
    public Student editClassInfo(Long schoolId, Long schoolClassId) {
        this.schoolId = schoolId;
        this.schoolClassId = schoolClassId;
        return this;
    }
}
