package com.everyschool.alarmservice.domain.alarm;

import com.everyschool.alarmservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Alarm extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @Column(nullable = false, updatable = false, length = 255)
    private String fcmToken;

    @Column(nullable = false)
    private Boolean isOpen;

    protected Alarm() {
        super();
    }

    @Builder
    private Alarm(String fcmToken, Boolean isOpen) {
        this();
        this.fcmToken = fcmToken;
        this.isOpen = isOpen;
    }
}
