package com.everyschool.alarmservice.domain.alarm;

import com.everyschool.alarmservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Column(nullable = false, updatable = false, length = 1)
    private String type;

    @Column(nullable = false, updatable = false)
    private Integer schoolYear;

    @Column(nullable = false, updatable = false)
    private Long senderId;

    @OneToMany(mappedBy = "alarmMaster", cascade = CascadeType.PERSIST)
    private List<Alarm> alarms = new ArrayList<>();

    protected AlarmMaster() {
        super();
    }

    @Builder
    private AlarmMaster(String title, String content, String type, Integer schoolYear, Long senderId, List<Alarm> alarms) {
        this();
        this.title = title;
        this.content = content;
        this.type = type;
        this.schoolYear = schoolYear;
        this.senderId = senderId;
        this.alarms = alarms;
    }

    public static AlarmMaster createAlarmMaster(String title, String content, String type, Integer schoolYear,
                                                Long senderId, Map<Long, String> recipientsInfo) {
        AlarmMaster alarmMaster = AlarmMaster.builder()
            .title(title)
            .content(content)
            .schoolYear(schoolYear)
            .type(type)
            .senderId(senderId)
            .alarms(new ArrayList<>())
            .build();

        for (Map.Entry<Long, String> entry : recipientsInfo.entrySet()) {
            Alarm.builder()
                .fcmToken(entry.getValue())
                .recipientId(entry.getKey())
                .alarmMaster(alarmMaster)
                .build();
        }

        return alarmMaster;
    }
}
