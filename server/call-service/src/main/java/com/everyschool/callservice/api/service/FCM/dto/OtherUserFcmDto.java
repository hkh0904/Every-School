package com.everyschool.callservice.api.service.FCM.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class OtherUserFcmDto {

    private String myUserKey;
    private String otherUserKey;
    private String senderName;
    private String cname;

    @Builder
    private OtherUserFcmDto(String myUserKey, String otherUserKey, String senderName, String cname) {
        this.myUserKey = myUserKey;
        this.otherUserKey = otherUserKey;
        this.senderName = senderName;
        this.cname = cname;
    }
}
