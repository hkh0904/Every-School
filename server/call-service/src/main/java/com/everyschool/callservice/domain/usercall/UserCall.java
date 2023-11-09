package com.everyschool.callservice.domain.usercall;

import com.everyschool.callservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "user_call")
public class UserCall extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_call_id")
    private Long id;

    @Column(nullable = false)
    private Long teacherId;

    @Column(nullable = false)
    private Long otherUserId;

    @Column(nullable = false, updatable = false, length = 1)
    private String sender;

    @Column(nullable = false, updatable = false, length = 20)
    private String senderName;

    @Column(nullable = false, updatable = false, length = 20)
    private String receiverName;

    private Boolean receiveCall;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    private String uploadFileName;
    private String storeFileName;

    @ColumnDefault("false")
    private Boolean isBad;

    private String sentiment;
    private Float neutral;
    private Float positive;
    private Float negative;

    protected UserCall() {
        super();
    }

    @Builder
    private UserCall(Long teacherId, Long otherUserId, String sender, String senderName, String receiverName,
                     Boolean receiveCall, LocalDateTime startDateTime, LocalDateTime endDateTime, String uploadFileName,
                     String storeFileName, Boolean isBad) {
        this();
        this.teacherId = teacherId;
        this.otherUserId = otherUserId;
        this.sender = sender;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.receiveCall = receiveCall;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.isBad = isBad;
    }

    public void updateCallInfo(String sentiment, Float neutral, Float positive, Float negative, Boolean isBad) {
        this.sentiment = sentiment;
        this.neutral = neutral;
        this.positive = positive;
        this.negative = negative;
        this.isBad = isBad;
    }
}
