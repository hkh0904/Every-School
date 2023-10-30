package com.everyschool.consultservice.domain.consult;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Title {

    @Column(nullable = false, updatable = false, length = 100)
    private String parentTitle;

    @Column(nullable = false, updatable = false, length = 100)
    private String teacherTitle;

    @Builder
    private Title(String parentTitle, String teacherTitle) {
        this.parentTitle = parentTitle;
        this.teacherTitle = teacherTitle;
    }
}
