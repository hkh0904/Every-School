package com.everyschool.callservice.api.controller.call.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class RecordStartRequest {

    @NotNull
    private String cname;

    @NotNull
    private String uid;

    @NotNull
    private String token;

    @NotNull
    private String userKey;

    @NotNull
    private String otherUserKey;

    @Builder
    private RecordStartRequest(String cname, String uid, String token, String userKey, String otherUserKey) {
        this.cname = cname;
        this.uid = uid;
        this.token = token;
        this.userKey = userKey;
        this.otherUserKey = otherUserKey;
    }
}
