package com.everyschool.callservice.api.controller.call.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class RecordStopRequest {

    @NotNull
    private String cname;

    @NotNull
    private String uid;

    @NotNull
    private String resourceId;

    @NotNull
    private String sid;

    @Builder
    private RecordStopRequest(String cname, String uid, String resourceId, String sid) {
        this.cname = cname;
        this.uid = uid;
        this.resourceId = resourceId;
        this.sid = sid;
    }
}
