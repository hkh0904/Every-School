package com.everyschool.userservice.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "P")
public class Parent extends User {

    @Column(nullable = false, updatable = false, length = 1)
    private String parentType;

    @Builder
    private Parent(String email, String pwd, String name, String birth, String userKey, int userCodeId, String parentType) {
        super(email, pwd, name, birth, userKey, userCodeId);
        this.parentType = parentType;
    }
}
