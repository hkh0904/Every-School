package com.everyschool.callservice.domain.callrecord;

import com.everyschool.callservice.domain.BaseEntity;
import com.everyschool.callservice.domain.call.UserCall;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "call_record")
public class UserCallDetails extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "call_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "call_id")
    private UserCall userCall;

//    @Column(nullable = false, updatable = false, length = 50)
//    private String content;
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime startDate;
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime endDate;

    private String content;
    private int start;
    private int length;
    private String sentiment;
    private Float neutral;
    private Float positive;
    private Float negative;

    protected UserCallDetails() { super(); }

    @Builder
    public UserCallDetails(UserCall userCall, String content, int start, int length, String sentiment, Float neutral,
                           Float positive, Float negative) {
        this();
        this.userCall = userCall;
        this.content = content;
        this.start = start;
        this.length = length;
        this.sentiment = sentiment;
        this.neutral = neutral;
        this.positive = positive;
        this.negative = negative;
    }
}
