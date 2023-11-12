package com.everyschool.callservice.api.controller.usercall.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TsFileKeyRequest {
    private String tsFileKey;

    @Builder
    public TsFileKeyRequest(String tsFileKey) {
        this.tsFileKey = tsFileKey;
    }
}
