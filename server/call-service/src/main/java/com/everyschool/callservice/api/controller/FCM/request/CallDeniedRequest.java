package com.everyschool.callservice.api.controller.FCM.request;

import com.everyschool.callservice.api.service.FCM.dto.CallDeniedDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CallDeniedRequest {

    @NotNull
    private String otherUserKey;

    @NotNull
    private String senderName;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    @Builder
    private CallDeniedRequest(String otherUserKey, String senderName, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.otherUserKey = otherUserKey;
        this.senderName = senderName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public CallDeniedDto toDto() {
        return CallDeniedDto.builder()
                .otherUserKey(this.otherUserKey)
                .senderName(this.senderName)
                .startDateTime(this.startDateTime)
                .endDateTime(this.endDateTime)
                .build();
    }

}
