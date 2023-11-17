package com.everyschool.callservice.api.controller.FCM.request;

import com.everyschool.callservice.api.service.FCM.dto.OtherUserFcmDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class OtherUserFcmRequest {
    @NotNull
    private String myUserKey;

    @NotNull
    private String otherUserKey;

    @NotNull
    private String senderName;

    @NotNull
    private String cname;

    @Builder
    public OtherUserFcmRequest(String myUserKey, String otherUserKey, String senderName, String cname) {
        this.myUserKey = myUserKey;
        this.otherUserKey = otherUserKey;
        this.senderName = senderName;
        this.cname = cname;
    }

    public OtherUserFcmDto toDto() {
        return OtherUserFcmDto.builder()
                .myUserKey(this.myUserKey)
                .otherUserKey(this.otherUserKey)
                .senderName(this.senderName)
                .cname(this.cname)
                .build();
    }
}
