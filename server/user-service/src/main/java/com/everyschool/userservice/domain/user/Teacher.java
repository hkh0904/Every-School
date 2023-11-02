package com.everyschool.userservice.domain.user;

import lombok.AccessLevel;
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

    public Teacher(Long schoolId, Long schoolClassId) {
        super();
        this.schoolId = schoolId;
        this.schoolClassId = schoolClassId;
    }
}
