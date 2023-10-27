package com.everyschool.callservice.domain.callrecord;

import com.everyschool.callservice.domain.BaseEntity;
import com.everyschool.callservice.domain.call.Call;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "call_record")
public class CallRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "call_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "call_id")
    private Call call;

    @Column(nullable = false, updatable = false, length = 50)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime startDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime endDate;

    protected CallRecord() { super(); }

    @Builder
    private CallRecord(Call call, String content, LocalDateTime startDate, LocalDateTime endDate) {
        this.call = call;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
