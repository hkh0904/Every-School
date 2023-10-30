package com.everyschool.reportservice.domain.report;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportContent {

    @Column(nullable = false, updatable = false, length = 100)
    private String reportWho;

    @Column(nullable = false, updatable = false, length = 100)
    private String reportWhen;

    @Column(nullable = false, updatable = false, length = 100)
    private String reportWhere;

    @Column(nullable = false, updatable = false, length = 100)
    private String reportWhat;

    @Column(updatable = false, length = 100)
    private String reportHow;

    @Column(updatable = false, length = 100)
    private String reportWhy;

    @Builder
    private ReportContent(String reportWho, String reportWhen, String reportWhere, String reportWhat, String reportHow, String reportWhy) {
        this.reportWho = reportWho;
        this.reportWhen = reportWhen;
        this.reportWhere = reportWhere;
        this.reportWhat = reportWhat;
        this.reportHow = reportHow;
        this.reportWhy = reportWhy;
    }
}
