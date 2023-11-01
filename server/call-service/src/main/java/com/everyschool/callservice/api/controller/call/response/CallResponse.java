package com.everyschool.callservice.api.controller.call.response;

import com.everyschool.callservice.domain.call.Call;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CallResponse {

    private Long callId;
    private String senderName;
    private String receiverName;
    private String sender;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String uploadFileName;
    private String storeFileName;
    private Boolean isBad;

    @Builder
    public CallResponse(Long callId, String senderName, String receiverName, String sender, LocalDateTime startDateTime,
                        LocalDateTime endDateTime, String uploadFileName, String storeFileName, Boolean isBad) {
        this.callId = callId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.sender = sender;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.isBad = isBad;
    }

    public static CallResponse of(Call call) {
        return CallResponse.builder()
                .callId(call.getId())
                .senderName(call.getSenderName())
                .receiverName(call.getReceiverName())
                .sender(call.getSender())
                .startDateTime(call.getStartDateTime())
                .endDateTime(call.getEndDateTime())
                .uploadFileName(call.getUploadFileName())
                .storeFileName(call.getStoreFileName())
                .isBad(call.getIsBad())
                .build();
    }
}
