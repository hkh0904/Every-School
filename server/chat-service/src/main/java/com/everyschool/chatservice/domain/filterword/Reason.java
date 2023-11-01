package com.everyschool.chatservice.domain.filterword;

import com.everyschool.chatservice.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reason extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reason_id")
    private Long id;

    private Long chatId;
    @Column(length = 50)
    @Size(max = 50)
    private String filterReason;

    @Builder
    private Reason(Long id, Long chatId, String filterReason) {
        this.id = id;
        this.chatId = chatId;
        this.filterReason = filterReason;
    }
}
