package com.everyschool.callservice.api.controller.call.request;

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
}
