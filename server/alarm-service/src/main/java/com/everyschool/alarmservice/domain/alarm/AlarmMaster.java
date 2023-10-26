package com.everyschool.alarmservice.domain.alarm;

import com.everyschool.alarmservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class AlarmMaster extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_master_id")
    private Long id;

    @Column(nullable = false, updatable = false, length = 50)
    private String title;

    @Column(nullable = false, updatable = false, length = 100)
    private String content;

    @Column(nullable = false, updatable = false)
    private Integer year;

    @Column(nullable = false, updatable = false)
    private Integer senderId;

    protected AlarmMaster() {
        super();
    }

    @Builder
    private AlarmMaster(String title, String content, Integer year, Integer senderId) {
        this();
        this.title = title;
        this.content = content;
        this.year = year;
        this.senderId = senderId;
    }
}
