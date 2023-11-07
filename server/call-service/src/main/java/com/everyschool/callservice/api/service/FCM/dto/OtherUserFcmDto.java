package com.everyschool.callservice.api.service.FCM.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
public class OtherUserFcmDto {

    private String otherUserKey;
    private String senderName;
    private String senderTel;
    private String cname;

    @Builder
    private OtherUserFcmDto(String otherUserKey, String senderName, String senderTel, String cname) {
        this.otherUserKey = otherUserKey;
        this.senderName = senderName;
        this.senderTel = senderTel;
        this.cname = cname;
    }
}
