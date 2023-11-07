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
    private String otherUserKey;

    @NotNull
    private String senderName;

    @NotNull
    private String senderTel;

    @NotNull
    private String cname;

    @Builder
    public OtherUserFcmRequest(String otherUserKey, String senderName, String senderTel, String cname) {
        this.otherUserKey = otherUserKey;
        this.senderName = senderName;
        this.senderTel = senderTel;
        this.cname = cname;
    }

    public OtherUserFcmDto toDto() {
        return OtherUserFcmDto.builder()
                .otherUserKey(this.otherUserKey)
                .senderName(this.senderName)
                .senderTel(this.senderTel)
                .cname(this.cname)
                .build();
    }
}
