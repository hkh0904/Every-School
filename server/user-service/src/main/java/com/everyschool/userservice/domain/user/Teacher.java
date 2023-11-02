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
@DiscriminatorValue(value = "T")
public class Teacher extends User {

    private Long schoolId;
    private Long schoolClassId;

    @Builder
    private Teacher(String email, String pwd, String name, String birth, String userKey, int userCodeId, Long schoolId, Long schoolClassId) {
        super(email, pwd, name, birth, userKey, userCodeId);
        this.schoolId = schoolId;
        this.schoolClassId = schoolClassId;
    }
}
