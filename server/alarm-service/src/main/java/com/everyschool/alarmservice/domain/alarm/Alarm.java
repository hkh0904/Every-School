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

    @Column(nullable = false, updatable = false)
    private Long recipientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_master_id")
    private AlarmMaster alarmMaster;

    protected Alarm() {
        super();
        this.isOpen = false;
    }

    @Builder
    private Alarm(String fcmToken, Long recipientId, AlarmMaster alarmMaster) {
        this();
        this.fcmToken = fcmToken;
        this.recipientId = recipientId;
        this.alarmMaster = alarmMaster;
    }
}
