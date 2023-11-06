package com.everyschool.callservice.domain.call;

import com.everyschool.callservice.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "call")
public class UserCall extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "call_id")
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

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = false, updatable = false, length = 200)
    private String uploadFileName;

    @Column(nullable = false, updatable = false, length = 200)
    private String storeFileName;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isBad;

    private String sentiment;
    private Float neutral;
    private Float positive;
    private Float negative;

    protected UserCall() { super(); }

    @Builder
    private UserCall(Long teacherId, Long otherUserId, String sender, String senderName, String receiverName,
                     LocalDateTime startDateTime, LocalDateTime endDateTime, String uploadFileName, String storeFileName,
                     Boolean isBad) {
        this();
        this.teacherId = teacherId;
        this.otherUserId = otherUserId;
        this.sender = sender;
        this.senderName = senderName;
        this.receiverName = receiverName;
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
