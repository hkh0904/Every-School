package com.everyschool.userservice.domain.user;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class StudentParentId implements Serializable {

    private Long parent;
    private Long student;
}
