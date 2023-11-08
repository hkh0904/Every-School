package com.everyschool.callservice.api.controller.FCM.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FCMNotificationRequest {
    private Long targetUserId;
    private String title;
    private String body;
    // private String image;
    // private Map<String, String> data;

    @Builder
    public FCMNotificationRequest(Long targetUserId, String title, String body) {
        this.targetUserId = targetUserId;
        this.title = title;
        this.body = body;
        // this.image = image;
        // this.data = data;
    }
}
