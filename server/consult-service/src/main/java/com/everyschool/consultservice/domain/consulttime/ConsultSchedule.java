package com.everyschool.consultservice.domain.consulttime;

import com.everyschool.consultservice.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultSchedule extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consult_schedule_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long teacherId;

    @Column(length = 50)
    private String description;

    @Column(nullable = false, length = 8)
    private String monday;

    @Column(nullable = false, length = 8)
    private String tuesday;

    @Column(nullable = false, length = 8)
    private String wednesday;

    @Column(nullable = false, length = 8)
    private String thursday;

    @Column(nullable = false, length = 8)
    private String friday;

    @Builder
    private ConsultSchedule(Long teacherId, String description, String monday, String tuesday, String wednesday, String thursday, String friday) {
        super();
        this.teacherId = teacherId;
        this.description = description;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }
}
