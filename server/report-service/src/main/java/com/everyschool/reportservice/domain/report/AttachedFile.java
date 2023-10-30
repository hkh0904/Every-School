package com.everyschool.reportservice.domain.report;

import com.everyschool.reportservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class AttachedFile extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attached_file_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private String uploadFileName;

    @Column(nullable = false, updatable = false)
    private String storeFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    protected AttachedFile() {
        super();
    }

    @Builder
    private AttachedFile(String uploadFileName, String storeFileName, Report report) {
        this();
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.report = report;
    }
}
