package com.everyschool.callservice.api.service.FCM.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class OtherUserFcmDto {

    private String otherUserKey;
    private String senderName;
    private String cname;

    @Builder
    private OtherUserFcmDto(String otherUserKey, String senderName,String cname) {
        this.otherUserKey = otherUserKey;
        this.senderName = senderName;
        this.cname = cname;
    }
}
