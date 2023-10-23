package com.everyschool.userservice.domain;

import lombok.Getter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
public class BaseEntity extends TimeBaseEntity{

    private boolean isDeleted;

    protected BaseEntity() {
        this.isDeleted = false;
    }
}
