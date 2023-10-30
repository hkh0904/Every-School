package com.everyschool.reportservice.domain.report;

import com.everyschool.reportservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Report extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(nullable = false, updatable = false, length = 100)
    private String title;

    @Embedded
    private ReportContent content;

    @Column(insertable = false, length = 500)
    private String result;

    @Column(nullable = false)
    private Integer progressStatusId;

    @Column(nullable = false, updatable = false)
    private Integer schoolYear;

    @Column(nullable = false, updatable = false)
    private Integer typeId;

    @Column(nullable = false, updatable = false)
    private Long schoolId;

    @Column(nullable = false, updatable = false)
    private Long userId;

    protected Report() {
        super();
        this.progressStatusId = 1;
    }

    @Builder
    private Report(String title, ReportContent content, Integer schoolYear, Integer typeId, Long schoolId, Long userId) {
        this();
        this.title = title;
        this.content = content;
        this.schoolYear = schoolYear;
        this.typeId = typeId;
        this.schoolId = schoolId;
        this.userId = userId;
    }
}
