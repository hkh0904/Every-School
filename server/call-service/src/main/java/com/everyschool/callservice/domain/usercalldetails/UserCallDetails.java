package com.everyschool.callservice.domain.usercalldetails;

import com.everyschool.callservice.domain.BaseEntity;
import com.everyschool.callservice.domain.usercall.UserCall;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "user_call_details")
public class UserCallDetails extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_call_details_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_call_id")
    private UserCall userCall;

    private String fileName;
    private String content;
    private int start;
    private int length;
    private String sentiment;
    private Float neutral;
    private Float positive;
    private Float negative;

    protected UserCallDetails() {
        super();
    }

    @Builder
    public UserCallDetails(String fileName, UserCall userCall, String content, int start, int length, String sentiment, Float neutral,
                           Float positive, Float negative) {
        this();
        this.fileName = fileName;
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
