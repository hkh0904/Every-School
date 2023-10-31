package com.everyschool.reportservice.domain.report;

import com.everyschool.reportservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Report extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(nullable = false, updatable = false, length = 100)
    private String title;

    @Column(nullable = false, updatable = false, length = 500)
    private String description;

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

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttachedFile> files = new ArrayList<>();

    protected Report() {
        super();
        this.progressStatusId = 1;
    }

    @Builder
    private Report(String title, String description, ReportContent content, Integer schoolYear, Integer typeId, Long schoolId, Long userId) {
        this();
        this.title = title;
        this.description = description;
        this.content = content;
        this.schoolYear = schoolYear;
        this.typeId = typeId;
        this.schoolId = schoolId;
        this.userId = userId;
    }

    //== 연관관계 편의 메서드 ==//

    public static Report createReport(String title, String description, ReportContent content, int schoolYear, int typeId, Long schoolId, Long userId, List<UploadFile> uploadFiles) {
        Report report = Report.builder()
            .title(title)
            .description(description)
            .content(content)
            .schoolYear(schoolYear)
            .typeId(typeId)
            .schoolId(schoolId)
            .userId(userId)
            .build();

        for (UploadFile uploadFile : uploadFiles) {
            AttachedFile.builder()
                .uploadFile(uploadFile)
                .report(report)
                .build();
        }

        return report;
    }
}
