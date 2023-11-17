package com.everyschool.reportservice.domain;

import lombok.Getter;

import javax.persistence.MappedSuperclass;

/**
 * 삭제 여부 공통 관리 클래스
 *
 * @author 임우택
 */
@MappedSuperclass
@Getter
public class BaseEntity extends TimeBaseEntity{

    private boolean isDeleted;

    protected BaseEntity() {
        this.isDeleted = false;
    }

    public void remove() {
        this.isDeleted = true;
    }
}
