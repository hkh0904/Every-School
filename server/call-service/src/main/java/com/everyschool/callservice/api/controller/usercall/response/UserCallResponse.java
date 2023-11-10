package com.everyschool.callservice.api.controller.usercall.response;

import com.everyschool.callservice.domain.usercall.UserCall;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCallResponse {

    private Long userCallId;
    private String senderName;
    private String receiverName;
    private String sender;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Boolean isBad;

    @Builder
    public UserCallResponse(Long userCallId, String senderName, String receiverName, String sender, LocalDateTime startDateTime,
                            LocalDateTime endDateTime, Boolean isBad) {
        this.userCallId = userCallId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.sender = sender;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isBad = isBad;
    }

    public static UserCallResponse of(UserCall userCall) {
        return UserCallResponse.builder()
                .userCallId(userCall.getId())
                .senderName(userCall.getSenderName())
                .receiverName(userCall.getReceiverName())
                .sender(userCall.getSender())
                .startDateTime(userCall.getStartDateTime())
                .endDateTime(userCall.getEndDateTime())
                .isBad(userCall.getIsBad())
                .build();
    }
}
