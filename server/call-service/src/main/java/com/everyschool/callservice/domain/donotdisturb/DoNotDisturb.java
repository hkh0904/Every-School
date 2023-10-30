package com.everyschool.callservice.domain.donotdisturb;

import com.everyschool.callservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "do_not_disturb")
public class DoNotDisturb extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "do_not_disturb_id")
    private Long id;

    @Column(nullable = false)
    private Long teacherId;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isActivate;

    protected DoNotDisturb() {
        super();
    }

    @Builder
    private DoNotDisturb(Long teacherId, LocalDateTime startTime, LocalDateTime endTime, Boolean isActivate) {
        this();
        this.teacherId = teacherId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActivate = isActivate;
    }
}
