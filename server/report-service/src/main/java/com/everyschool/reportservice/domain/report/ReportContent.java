package com.everyschool.reportservice.domain.report;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
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
}
